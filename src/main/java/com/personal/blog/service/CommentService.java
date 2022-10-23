package com.personal.blog.service;


import com.personal.blog.pojo.Comment;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface CommentService {
    List<Comment> queryComments();

    int deleteCommentById(Long id);

    Object querySonComment(Long id,String type);

    int updateCommentById(Comment comment);

    int changeAuthorizationByCommentID(Long commentID);
}
