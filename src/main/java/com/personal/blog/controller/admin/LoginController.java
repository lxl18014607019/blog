package com.personal.blog.controller.admin;


import com.personal.blog.pojo.User;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login(HttpServletRequest request){

        if (request.getSession().getAttribute("user") != null){
            return "admin/index";
        }

        return "admin/login";
    }

    @PostMapping("/login")
    public String login
            (String username, String password, Model model, HttpServletRequest request){

        //接收到username,调用service层进行查询
        User user = userService.queryUserByUsernameAndPassword(username, MD5.getMD5(password));

        //打印user-测试
        //System.out.println(user);

        //存入session
        if (user != null){
            request.getSession().setAttribute("user",user);
            //60分钟未操作销毁
            request.getSession().setMaxInactiveInterval(2*30*60);
        }
        else
            model.addAttribute("message","用户名或密码错误!");

        return user != null ? "redirect:/admin/index" : "admin/login";
    }


}
