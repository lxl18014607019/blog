package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.blog.mapper.BlogCommentMapper;
import com.personal.blog.mapper.BlogMapper;
import com.personal.blog.pojo.Background;
import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class BlogCommentMapperImpl {

    @Autowired
    public BlogCommentMapper blogCommentMapper;

    public BlogComment selectByPrimaryKey(Long id){
        return blogCommentMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return blogCommentMapper.deleteById(id);
    }

    public int insert(BlogComment blogComment){
        return blogCommentMapper.insert(blogComment);
    }

    public int updateByPrimaryKey(BlogComment blogComment){
        return blogCommentMapper.updateById(blogComment);
    }

    public List<BlogComment> selectCommentsByBlogId(Long blogId){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        queryWrapper.eq("authorization", 1);
        List<BlogComment> blogComments = blogCommentMapper.selectList(queryWrapper);
        for(BlogComment blogComment:blogComments){
            //在这里进行子评论绑定，父评论绑定在BuildCommentName方法里绑定
            blogComment.setSonComment(selectSonComments(blogComment.getId()));
        }
        return blogComments;
    }

    public List<BlogComment> selectCommentsByBlogId(Long blogId,int count){
        Page<BlogComment> page = new Page<>(0, count);
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id",blogId);
        queryWrapper.eq("authorization", 1);

        Page<BlogComment> blogCommentPage = blogCommentMapper.selectPage(page, queryWrapper);
        List<BlogComment> blogComments = blogCommentPage.getRecords();
        for(BlogComment blogComment:blogComments){
            //在这里进行子评论绑定，父评论绑定在BuildCommentName方法里绑定
            blogComment.setSonComment(selectSonComments(blogComment.getId()));
        }
        return blogComments;
    }

    public List<BlogComment> selectSonComments(Long parentId){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.eq("authorization", 1);
        List<BlogComment> blogComments = blogCommentMapper.selectList(queryWrapper);
        for(BlogComment blogComment:blogComments){
            blogComment.setSonComment(selectSonComments(blogComment.getId()));
        }
        return blogComments;
    }

    public BlogComment selectCommentsByCommentId(Long commentId){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", commentId);
        BlogComment blogComment = blogCommentMapper.selectOne(queryWrapper);
        if(blogComment!=null){
            blogComment.setSonComment(selectSonComments(blogComment.getId()));
        }
        return blogComment;
    }

    public int selectCommentsCountByBlogId(Long blogId){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        queryWrapper.eq("parent_id", 0);
        queryWrapper.eq("authorization", 1);
        return blogCommentMapper.selectCount(null);
    }


    public int selectCommentsCount(){
        return blogCommentMapper.selectCount(null);
    }


}

