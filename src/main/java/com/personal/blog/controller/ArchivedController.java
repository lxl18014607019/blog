package com.personal.blog.controller;


import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.User;
import com.personal.blog.service.BlogService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * @description:
 * @author: luxinlong
 * @date: 2022/10/12 23:48
 * @param: null
 * @return: null
 **/
@Controller
@RequestMapping("/archived")
public class ArchivedController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    //范围
    private Integer range = 30;

    @GetMapping("/index")
    public String archived(Model model){

        //通过创建日期排序
        List<Blog> blogs = blogService.queryBlogOrderByCreateTimeLimit(range);

        //User
        User user = userService.queryUser();

        //加入
        model.addAttribute("user",user);
        model.addAttribute("blogs",blogs);
        //给予ok默认值1
        model.addAttribute("ok",1);
        return "archived";
    }


    @PostMapping("/loadmore")
    public String loadmore(@RequestParam("page") Integer currentPage, Model model){

        //查询范围
        int total = currentPage * range;

        int ok = 1;

        //查询博客,数量为当前页数*范围
        List<Blog> blogs = blogService.queryBlogOrderByCreateTimeLimit(total);

        if (total >= blogService.queryBlogCount())
            ok = 0;

        model.addAttribute("ok",ok);
        model.addAttribute("blogs",blogs);
        return "archived :: time_line";
    }
}
