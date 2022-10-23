package com.personal.blog.service.implement;


import com.personal.blog.mapper.implement.FriendsMapperImpl;
import com.personal.blog.pojo.Friends;
import com.personal.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class FriendServiceImp implements FriendService {

    @Autowired
    private FriendsMapperImpl friendDao;

    @Override
    public List<Friends> queryAuthorizationFirends() {

        List<Friends> friends = friendDao.selectAuthorizationFirends();

        return friends;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer AddFriend(Friends friends) {

        friends.setAuthorization(false);

        return friendDao.insert(friends);
    }

    @Override
    public List<Friends> queryAllFriends() {

        return friendDao.selectAllFriends();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int authorization(Long id, boolean status) {

        return friendDao.updateAuthorizationByPrimaryKey(id,status);
    }

    @Override
    public int deleteFriendById(Long id) {
        return friendDao.deleteByPrimaryKey(id);
    }


}
