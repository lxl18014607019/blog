package com.personal.blog.controller;

import com.personal.blog.pojo.Friends;
import com.personal.blog.service.FriendService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:FriendController
 * Package:com.myblog.blog.controller
 * Description:
 *
 * @Date:2021/11/9 22:48
 * @com.chuangmei
 */
@Controller
public class FriendController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @RequestMapping("/friend")
    public String index(Model model) {


        model.addAttribute("friends",friendService.queryAuthorizationFirends());
        model.addAttribute("user",userService.queryUser());

        return "friends";
    }


    @RequestMapping("/friend/add")
    public String add(Friends friends, Model model) {

        Integer ok = 0;
        String message = "添加失败 , 请以留言的方式添加!!";

        if (friends != null) {
            ok = friendService.AddFriend(friends);
            message = "添加成功 , 审核后显示!!";
        }

        model.addAttribute("ok" , ok);
        model.addAttribute("message" , message);

        return "friends :: friends-hint";
    }

}
