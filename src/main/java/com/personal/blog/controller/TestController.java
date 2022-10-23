package com.personal.blog.controller;


import com.personal.blog.pojo.Background;
import com.personal.blog.service.BackgroundService;
import com.personal.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:TestController
 * Package:com.myblog.blog.controller
 * Description:
 *
 * @Date:2021/5/9 10:42
 * @com.chuangmei
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BackgroundService backgroundService;

    @GetMapping("index")
    @ResponseBody
    public List index(){

        return blogService.getAllBlog();
    }

    @GetMapping("blog")
    @ResponseBody
    public List blog(String title){

        System.out.println(title);

        return blogService.queryBlogByTitle(title,false);
    }

    @GetMapping("homedata")
    @ResponseBody
    public List homedata(@RequestParam(required = false,defaultValue = "5") int limit){

        System.out.println(limit);

        List<Background> backgrounds = backgroundService.queryAllLimit(limit);

        return backgrounds;
    }



}
