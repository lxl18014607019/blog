package com.personal.blog.controller.admin;


import com.personal.blog.pojo.User;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        //session置空
        request.getSession().setAttribute("user",null);

        return "redirect:/";
    }


    @GetMapping("/editouser")
    public String editor(Model model){

        //user
        User user = userService.queryUser();

        model.addAttribute("user",user);

        return "admin/editoruser";
    }

    @PostMapping("/updateuser")
    public String updateUser(User user){

        //修改
        int count = userService.updateUserSelective(user);

        return count > 0 ? "redirect:/admin/index" : "error/error";
    }

    @GetMapping("/changepassword")
    public String changePass(Model model){

        //user
        User user = userService.queryUser();
        model.addAttribute("user",user);

        return "admin/editorpass";
    }

    @PostMapping("/ediPass")
    public String ediPass(String originalPass,String newPass,String question,HttpServletRequest request,Model model){

        int count = userService.updatePassword(originalPass,newPass,question);

        if (count > 0){
            request.getSession().setAttribute("user",null);
        }else{
            model.addAttribute("errorMessage","信息错误!");
        }

        return count > 0 ? "redirect:/" : "admin/editorpass";
    }

    @GetMapping("/showorhide")
    @ResponseBody
    public Integer showorhide(Boolean state) {

        System.out.println(state);

        // 修改状态
        int count = userService.changeShow(state);

        return count;
    }

}
