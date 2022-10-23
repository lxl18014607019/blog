package com.personal.blog.service;


import com.personal.blog.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
* @description:
* @author: luxinlong
**/

public interface UserService {

    /*登录查询接口*/
    User queryUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User queryUser();

    int updateUserSelective(User user);

    int updatePassword(@Param("originalPass") String originalPass, @Param("newPass") String newPass, @Param("question") String question);

    int updateFirstPicture(String url);

    int updateUserTheme(String theme);

    int changeShow(Boolean val);
}
