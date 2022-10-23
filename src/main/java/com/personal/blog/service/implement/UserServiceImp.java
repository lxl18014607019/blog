package com.personal.blog.service.implement;


import com.personal.blog.mapper.implement.UserMapperImpl;
import com.personal.blog.pojo.User;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @description:
* @author: luxinlong
**/

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapperImpl userDao;

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {

        //调用Dao层进行查询
        User user = userDao.selectByUsernameAndPassword(username,password);

        return user;
    }

    @Override
    public User queryUser() {
        User user = userDao.selectUser();
        return user;
    }

    //修改user
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int updateUserSelective(User user) {
        int count = userDao.updateByPrimaryKey(user);
        return count;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int updatePassword(String originalPass, String newPass, String question) {
        int count = userDao.updatePassword(MD5.getMD5(originalPass),MD5.getMD5(newPass),question);
        return count;
    }

    /*修改首图*/
    @Override
    public int updateFirstPicture(String url) {
        return userDao.updateByFirstPicture(url);
    }

    //更改主题接口
    @Override
    public int updateUserTheme(String theme) {
        int success = userDao.updateByTheme(theme);
        return success;
    }

    @Override
    public int changeShow(Boolean val) {
        return userDao.changeShow(val);
    }
}
