package com.personal.blog.service.implement;


import com.personal.blog.mapper.implement.CommentMapperImpl;
import com.personal.blog.pojo.Comment;
import com.personal.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentMapperImpl dao;

    @Override
    public List<Comment> queryComments() {
        List<Comment> comments = dao.selectComments();
        return comments;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int deleteCommentById(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public Object querySonComment(Long id,String type) {
        List<Comment> comments = dao.selectSonByPrimaryKey(id);
        if (type.equals("Integer")){
            return comments.size();
        }else {
            return comments;
        }
    }

    /**
     * 修改评论
     * @return
     */
    @Override
    public int updateCommentById(Comment comment) {

        return dao.updateByPrimaryKeySelective(comment);
    }

    @Override
    public int changeAuthorizationByCommentID(Long commentID) {

        return dao.updateAuthorizationByCommentID(commentID);
    }
}
