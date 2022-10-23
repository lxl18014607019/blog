package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @description:
* @author: luxinlong
**/
@Data
@TableName(value = "t_comment")
public class Comment {
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

//    private Blog blog;
}