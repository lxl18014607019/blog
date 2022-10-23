package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.UserMapper;
import com.personal.blog.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class UserMapperImpl {
    @Autowired
    private UserMapper userMapper;

    public User selectByPrimaryKey(Long id){
        return userMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return userMapper.deleteById(id);
    }


    public int insert(User user){
        return userMapper.insert(user);
    }

    public int updateByPrimaryKey(User user){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        return userMapper.update(user,updateWrapper);
    }

    public User selectByUsernameAndPassword(String userName,String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("password",password);
        return userMapper.selectOne(queryWrapper);
    }

    public User selectUser(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return userMapper.selectOne(queryWrapper);
    }

    public int updatePassword(String newPassword,String originalPassword,String question){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", newPassword);
        updateWrapper.eq("password", originalPassword).eq("question", question);
        return userMapper.update(null,updateWrapper);
    }

    public int simpleUpdate(String column,Object value){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(column, value);
        return userMapper.update(null,updateWrapper);
    }

    public int updateByFirstPicture(String url){
        return simpleUpdate("first_picture",url);
    }

    public int updateByTheme(String theme){
        return simpleUpdate("theme",theme);
    }

    public int changeShow(Boolean val){
        return simpleUpdate("info_state", val);
    }



}

