package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_comment")
public class BlogComment {
    private Long id;

    private String content;

    private String avatar;

    private String createTime;

    private String email;

    private String nickName;

    private Long blogId;

    private Long parentId;

    private Boolean adminComment;

    private Boolean authorization;

    @TableField(exist = false)
    List<BlogComment> sonComment;

    @TableField(exist = false)
    //父级评论
    private BlogComment parentComment;

}