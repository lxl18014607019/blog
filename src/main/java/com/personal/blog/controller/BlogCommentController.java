package com.personal.blog.controller;

import com.alibaba.fastjson.JSON;

import com.personal.blog.pojo.BlogComment;
import com.personal.blog.pojo.User;
import com.personal.blog.service.BlogCommentService;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.EmojiUtility;
import com.vdurmont.emoji.EmojiParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName:BlogCommentController
 * Package:com.myblog.blog.controller.admin
 * Description:
 *
 * @Date:2020/7/29 10:52
 * @com.chuangmei
 */
@Controller
@RequestMapping("/blogcomment")
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private UserService userService;

    //comments
    @GetMapping("/comments/{blogid}")
    public String comments(@PathVariable Long blogid, Model model){

        //user
        User user = userService.queryUser();

        //comments
        List<BlogComment> comments = blogCommentService.queryCommentsByBlogId(blogid);

        for (BlogComment comment : comments){
            comment.setContent(EmojiParser.parseToUnicode(comment.getContent()));
            //如果有子评论
            if (comment.getSonComment().size() > 0){
                //遍历子评论解析表情
                for (BlogComment soncomment : comment.getSonComment())
                    soncomment.setContent(EmojiParser.parseToUnicode(soncomment.getContent()));
            }
        }

        model.addAttribute("comments",comments);
        model.addAttribute("user",user);
        // 这两个条件判断是否显示评论"加载更多"按钮 当parentBlogComment > commentCount时,会显示,此处查询所有,就不显示
        model.addAttribute("parentBlogComment",0);
        model.addAttribute("commentCount",1);

        return "blog :: commentContent";
    }

    // 限制数量查询文章评论
    @PostMapping("/limit")
    public String commentsLimit(Long blogid ,Integer count, Model model){

        // 限制查询博客评论数量
        List<BlogComment> comments = blogCommentService.queryCommentsByBlogIdLimit(blogid,count);

        //user
        User user = userService.queryUser();

        // 查询对应博客下的所有评论数量
        int parentBlogComment = blogCommentService.queryCommentCountByBlogId(blogid);

        model.addAttribute("comments",comments);
        model.addAttribute("user",user);
        model.addAttribute("parentBlogComment",parentBlogComment);
        model.addAttribute("commentCount",count);

        return "blog :: commentContent";
    }

    @PostMapping("/comment")
    public String comment(BlogComment blogComment, HttpServletRequest request){

        // 替换敏感词
        // blogComment.setContent(WordFilter.replaceWords(blogComment.getContent()));

        //查询出管理员的nickname
        User user = userService.queryUser();

        //判断是否为管理员评论
        if (user.getNickName().equals(blogComment.getNickName()) && request.getSession().getAttribute("user") != null)
            blogComment.setAdminComment(true);
        else
            blogComment.setAdminComment(false);

        //将评论的表情转换
        blogComment.setContent(EmojiUtility.convertEmojiToStr(blogComment.getContent()));

        int count = blogCommentService.addBlogComment(blogComment);

        //获取blogid
        Long blogid = blogComment.getBlogId();

        return count > 0 ? "redirect:/blogcomment/comments/" + blogid : "error/error";
    }

    /**
     * 获取评论的json格式
     * @return
     */
    @GetMapping("/commentjson")
    @ResponseBody
    public String commentJson(@RequestParam("id") Long id) {

        System.out.println(id);

        BlogComment comment = blogCommentService.queryCommentByCommentId(id);
        String json = JSON.toJSONString(comment);
        return json;
    }



}
