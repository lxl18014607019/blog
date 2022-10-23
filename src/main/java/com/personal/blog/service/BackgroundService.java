package com.personal.blog.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.blog.pojo.Background;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface BackgroundService extends IService<Background> {

    int checkurlRepitition(String url);

    int addSelective(Background background);

    List<Background> queryAll();

    int deleteUrl(Long id);

    List<Background> queryAllLimit(int count);

    int queryCount();
}
