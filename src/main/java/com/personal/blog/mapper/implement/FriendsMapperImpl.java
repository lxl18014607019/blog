package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.FriendsMapper;
import com.personal.blog.pojo.Friends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class FriendsMapperImpl {
    @Autowired
    private FriendsMapper friendsMapper;

    public Friends selectByPrimaryKey(Long id){
        return friendsMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return friendsMapper.deleteById(id);
    }

    public int insert(Friends friends){
        return friendsMapper.insert(friends);
    }

    public int updateByPrimaryKeySelective(Friends friends){
        UpdateWrapper<Friends> updateWrapper = new UpdateWrapper<>();
        return friendsMapper.update(friends,updateWrapper);
    }

//    查询已授权的友联
    public List<Friends> selectAuthorizationFirends(){
        QueryWrapper<Friends> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authorization", 1);
        return friendsMapper.selectList(queryWrapper);
    }

    public List<Friends> selectAllFriends(){
        return friendsMapper.selectList(null);
    }

    public int updateAuthorizationByPrimaryKey(Long id,boolean status){
        UpdateWrapper<Friends> updateWrapper = new UpdateWrapper<>();
        Friends friends=new Friends();
        friends.setId(id);
        friends.setAuthorization(status);
        return friendsMapper.update(friends,updateWrapper);
    }
}

