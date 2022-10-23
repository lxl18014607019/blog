package com.personal.blog.controller.admin;


import com.personal.blog.pojo.Comment;
import com.personal.blog.service.CommentService;
import com.personal.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:AdminComments
 * Package:com.myblog.blog.controller.admin
 * Description:
 *
 * @Date:2021/4/17 16:58
 * @com.chuangmei
 */
@Controller
@RequestMapping("/admin")
public class AdminComments {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/comments")
    public String index(Model model){


        model.addAttribute("comments",commentService.queryComments());
        model.addAttribute("user",userService.queryUser());

        return "admin/comments";
    }

    @RequestMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") Long id,Model model){

        // 查询此评论是否有子评论
        Integer parentcount = (Integer) commentService.querySonComment(id,"Integer");

        int count = 0;

        if (parentcount == 0){
            // 删除
            count = commentService.deleteCommentById(id);
        }
        model.addAttribute("comments",commentService.queryComments());

        if (count <= 0) {
            model.addAttribute("message","您在删除评论的时候出错了! sql语句返回的count值小于0!");
        }

        return count > 0 ? "admin/comments :: comments_fragment" : "error/error";
    }

    @RequestMapping("/comment/update")
    public String updateComment
            (@RequestParam("id") Long id , @RequestParam("content") String content, Model model) {

        Comment comment = new Comment();

        comment.setId(id);
        comment.setContent(content);

        // 通过id修改对应评论
        int count = commentService.updateCommentById(comment);

        model.addAttribute("comments",commentService.queryComments());

        if (count <= 0) {
            model.addAttribute("message","您在修改评论的时候出错了! sql语句返回的count值小于0!");
        }

        return count > 0 ? "admin/comments :: comments_fragment" : "error/error";
    }

    @RequestMapping("/comment/authorization")
    public String authorizationComment(@RequestParam("commentid") Long commentid,Model model) {

        // 修改授权
        int count = commentService.changeAuthorizationByCommentID(commentid);

        model.addAttribute("comments",commentService.queryComments());

        return count > 0 ? "admin/comments :: comments_fragment" : "error/error";
    }

}
