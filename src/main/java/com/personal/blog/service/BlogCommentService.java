package com.personal.blog.service;



import com.personal.blog.pojo.BlogComment;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface BlogCommentService {
    //发布评论
    int addBlogComment(BlogComment blogComment);

    List<BlogComment> queryCommentsByBlogId(Long valueOf);

    int queryCommentCount();

    List<BlogComment> queryCommentsByBlogIdLimit(Long blogid, Integer count);

    int queryCommentCountByBlogId(Long blogid);

    BlogComment queryCommentByCommentId(Long id);
}
