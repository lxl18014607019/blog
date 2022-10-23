package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.BlogMapper;
import com.personal.blog.mapper.CommentMapper;
import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class CommentMapperImpl {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    public Comment selectByPrimaryKey(Long id){
        return commentMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return commentMapper.deleteById(id);
    }

    public int insert(Comment comment){
        return commentMapper.insert(comment);
    }

    public int updateByPrimaryKeySelective(Comment comment){
        UpdateWrapper<Comment> updateWrapper = new UpdateWrapper<>();
        return commentMapper.update(comment,updateWrapper);
    }

    public List<Comment> selectComments(){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return commentMapper.selectList(queryWrapper);
    }

    public List<String> selectBlog(Long blogId){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", blogId);
        queryWrapper.select("title");
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        List<String> titles=new ArrayList<>();
        for(Blog blog:blogs){
            titles.add(blog.getTitle());
        }
        return titles;
    }

    public List<Comment> selectSonByPrimaryKey(Long parentId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return commentMapper.selectList(queryWrapper);
    }

//    显示隐藏评论
    public int updateAuthorizationByCommentID(Long id){
        UpdateWrapper<Comment> updateWrapper = new UpdateWrapper<>();
        Comment comment=new Comment();
        comment.setId(id);
        updateWrapper.setSql(true,"authorization = ! authorization");
        return commentMapper.update(comment, updateWrapper);
    }
}

