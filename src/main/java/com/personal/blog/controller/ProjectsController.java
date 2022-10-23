package com.personal.blog.controller;


import com.personal.blog.pojo.Project;
import com.personal.blog.pojo.User;
import com.personal.blog.service.CodeService;
import com.personal.blog.service.ProjectService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    // userService
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CodeService codeService;

    @GetMapping("/index")
    public String index(Model model) {

        //ModelAndView modelAndView = new ModelAndView("/myprojects");

        // 查询项目
        Project[] projects =  projectService.queryProjects();

        User user = userService.queryUser();

        model.addAttribute("user",user);
        model.addAttribute("projects",projects);


        return "myprojects";
    }

    @PostMapping("/code")
    @ResponseBody
    public String code(@RequestParam("code") String code, HttpServletRequest request) {

        // 验证code
        Integer count = codeService.verificationCode(code);

        if (count == null) {
            return "验证码错误";
        }

        if (count > 0) {
            request.getSession().setAttribute("code",code);
            //60分钟未操作销毁
            request.getSession().setMaxInactiveInterval(2*30*60);

            return "success";
        } else {
            return "验证码错误";
        }
    }

}
