package com.personal.blog.controller;

import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:CodeController
 * Package:com.myblog.blog.controller
 * Description:
 *
 * @Date:2021/11/11 21:31
 * @com.chuangmei
 */
@Controller
@RequestMapping("code")
public class CodeController {

    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String index(Model model) {

        model.addAttribute("user",userService.queryUser());

        return "code";
    }

}
