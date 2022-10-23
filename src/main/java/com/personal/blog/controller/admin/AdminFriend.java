package com.personal.blog.controller.admin;


import com.personal.blog.service.FriendService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/admin/friend")
public class AdminFriend {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @RequestMapping("/friends")
    public String goAuthorization(Model model) {

        model.addAttribute("friends",friendService.queryAllFriends());
        model.addAttribute("user",userService.queryUser());

        return "admin/friends-authorization";
    }

    @RequestMapping("/authorization")
    public String authorization(Long id,boolean status,Model model) {

        int count = friendService.authorization(id,status);

        if (count > 0) {
            model.addAttribute("friends",friendService.queryAllFriends());
            model.addAttribute("user",userService.queryUser());
        }else {
            model.addAttribute("message","修改失败了!!");
            model.addAttribute("user",userService.queryUser());
        }

        return count > 0 ? "admin/friends-authorization :: friendsfragment" : "error/error";
    }

    @PostMapping("/delete")
    public String deleteFriend(Long id, Model model) {

        int count = friendService.deleteFriendById(id);

        if (count > 0) {
            model.addAttribute("friends",friendService.queryAllFriends());
        }else {
            model.addAttribute("message","删除失败了!");
            model.addAttribute("user",userService.queryUser());
        }

        return count > 0 ? "admin/friends-authorization :: friendsfragment" : "error/error";
    }

}
