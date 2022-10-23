package com.personal.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }


}
