package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.MessageboardMapper;
import com.personal.blog.pojo.Messageboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class MessageboardMapperImpl {
    @Autowired
    private MessageboardMapper messageboardMapper;

    public Messageboard selectByPrimaryKey(Long id){
        return messageboardMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return messageboardMapper.deleteById(id);
    }

    public int insert(Messageboard messageboard){
        return messageboardMapper.insert(messageboard);
    }

    public int updateByPrimaryKey(Messageboard messageboard){
        UpdateWrapper<Messageboard> updateWrapper = new UpdateWrapper<>();
        return messageboardMapper.update(messageboard,updateWrapper);
    }

    public List<Messageboard> selectMessageBoard(){
        QueryWrapper<Messageboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0).eq("authorization", 1);
        queryWrapper.orderByDesc("create_time");
        return messageboardMapper.selectList(queryWrapper);
    }

    public List<Messageboard> selectSonMesssage(Long parentId){
        QueryWrapper<Messageboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId", parentId).eq("authorization", 1);
        queryWrapper.orderByDesc("create_time");
        return messageboardMapper.selectList(queryWrapper);
    }

    public List<Messageboard> selectAllMessageBoard(){
        QueryWrapper<Messageboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return messageboardMapper.selectList(queryWrapper);
    }

    public int updateAuthorizationByMessageID(Long id){
        UpdateWrapper<Messageboard> updateWrapper = new UpdateWrapper<>();
        Messageboard messageboard=new Messageboard();
        messageboard.setId(id);
        updateWrapper.setSql(true, "authorization = ! authorization");
        return messageboardMapper.update(messageboard,updateWrapper);
    }
}

