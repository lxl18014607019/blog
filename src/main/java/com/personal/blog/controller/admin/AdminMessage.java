package com.personal.blog.controller.admin;


import com.personal.blog.pojo.Messageboard;
import com.personal.blog.service.MessageBoardService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/

@Controller
@RequestMapping("/admin")
public class AdminMessage {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageBoardService messageBoardService;


    @RequestMapping("/messages")
    public String messages(Model model) {

        model.addAttribute("user",userService.queryUser());
        // model.addAttribute("messages",messageBoardService.queryMessageAllBoard());
        model.addAttribute("messagesboard",messageBoardService.queryMessageBoardAll());

        return "admin/messages";
    }

    @RequestMapping("/messages/delete")
    public String deleteMessage(Long id,Model model) {

        int count  = messageBoardService.deleteMessage(id);

        if (count > 0) {
            model.addAttribute("user",userService.queryUser());
            /// model.addAttribute("messages",messageBoardService.queryMessageAllBoard());
            model.addAttribute("messagesboard",messageBoardService.queryMessageAllBoard());
        }else {
            model.addAttribute("message","删除失败!!");
        }

        return count > 0 ? "admin/messages :: messagesfragments" : "error/error";

    }

    @RequestMapping("/messages/json")
    @ResponseBody
    public List<Messageboard> messagesJson() {

        List<Messageboard> messageboards = messageBoardService.queryMessageAllBoard();

        return messageboards;
    }

    /**
     * 授权评论是否显示
     */
    @PostMapping("/messages/authorization")
    public String authorizationMessage(@RequestParam("id") Long id, Model model) {

        int count = messageBoardService.changeAuthorizationByMessageID(id);

        if (count > 0){
            model.addAttribute("user",userService.queryUser());
            model.addAttribute("messagesboard",messageBoardService.queryMessageBoardAll());
        }

        return count > 0 ? "admin/messages :: messagesfragments" : "error/error";
    }
}
