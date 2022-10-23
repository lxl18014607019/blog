package com.personal.blog.controller;


import com.personal.blog.service.BlogService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:TimelineController
 * Package:com.myblog.blog.controller
 * Description:
 *
 * @Date:2021/3/1 20:14
 * @com.chuangmei
 */
@Controller
@RequestMapping("timeline")
public class TimelineController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("index")
    public String index(Model model){

        model.addAttribute("blogs",blogService.queryBlogByRecordLife());
        model.addAttribute("user",userService.queryUser());

        return "timeline";
    }

}
