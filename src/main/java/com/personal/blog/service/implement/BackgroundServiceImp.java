package com.personal.blog.service.implement;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.blog.mapper.BackgroundMapper;
import com.personal.blog.mapper.implement.BackgroundMapperImpl;
import com.personal.blog.pojo.Background;
import com.personal.blog.service.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class BackgroundServiceImp extends ServiceImpl<BackgroundMapper,Background> implements BackgroundService {

    @Autowired
    public BackgroundMapperImpl backgroundMapper;


    @Override
    public int checkurlRepitition(String url) {
        return backgroundMapper.countByUrl(url);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addSelective(Background background) {

        return backgroundMapper.insert(background);
    }

    /*查询所有*/
    @Override
    public List<Background> queryAll() {

        return backgroundMapper.selectAll();
    }

    /*删除*/
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int deleteUrl(Long id) {

        return backgroundMapper.deleteById(id);
    }

    //加载更多图片-局部刷新
    @Override
    public List<Background> queryAllLimit(int count) {
        Page<Background> backgroundPage = backgroundMapper.selectAll(count);
        List<Background> records = backgroundPage.getRecords();
        return records;
    }

    //查询图片个数
    @Override
    public int queryCount() {
        return backgroundMapper.countAll();
    }
}
