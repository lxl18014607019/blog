package com.personal.blog.service;


import com.personal.blog.pojo.Friends;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface FriendService {

    // 查询已授权的友链
    List<Friends> queryAuthorizationFirends();

    Integer AddFriend(Friends friends);

    List<Friends> queryAllFriends();

    int authorization(Long id, boolean status);

    int deleteFriendById(Long id);
}
