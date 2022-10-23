package com.personal.blog.controller;


import com.personal.blog.pojo.Messageboard;
import com.personal.blog.pojo.User;
import com.personal.blog.service.MessageBoardService;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.EmojiUtility;
import com.personal.blog.utils.WordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * @description:
 * @author: luxinlong
 * @date: 2022/10/12 23:50
 * @param: null
 * @return: null
 **/
@Controller
public class MessageBoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageBoardService messageBoardService;


    /*跳转留言板*/
    @GetMapping("/messageboard")
    public String board(Model model){

        //messageboard
        List<Messageboard> messageboards = messageBoardService.queryMessageBoard();

        //user
        model.addAttribute("user",userService.queryUser());
        model.addAttribute("messageboards",messageboards);
        return "messageboard";
    }

    /*新增留言信息*/
    @PostMapping("/addmessage")
    public String addMessage(Messageboard messageboard, Model model, HttpServletRequest request){

        // 替换敏感词
        messageboard.setContent(WordFilter.replaceWords(messageboard.getContent()));

        //User
        User user = (User) request.getSession().getAttribute("user");

        //判断
        if (user != null){
            if (user.getNickName().equals(messageboard.getNickName()) && user.getEmail().equals(messageboard.getEmail())){
                messageboard.setAdmin(true);
                messageboard.setAvatar(user.getAvatar());
            }
        }else{
            messageboard.setAdmin(false);
            if (messageboard.getAvatar() == null || "".equals(messageboard.getAvatar())){
                messageboard.setAvatar("/images/avatar/default.jpg");
            }
        }

        //表情处理
        messageboard.setContent(EmojiUtility.convertEmojiToStr(messageboard.getContent()));

        //添加留言
        int count = messageBoardService.addMessage(messageboard);
        //如果添加成功
        if(count > 0){
            List<Messageboard> messageboards = messageBoardService.queryMessageBoard();
            model.addAttribute("messageboards",messageboards);
        }

        //加入user
        model.addAttribute("user",userService.queryUser());

        return count > 0 ? "messageboard :: messageboard" : "error/error";
    }




}
