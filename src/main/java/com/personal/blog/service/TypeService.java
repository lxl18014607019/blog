package com.personal.blog.service;



import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.Type;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface TypeService {
    int setType(Type type);

    int queryTypeBynameCount(String typename);

    List<Type> queryAllType();

    Type queryTypeById(Long id);

    int updateTypeByType(Type type);

    int deleteTypeById(Long id);

    List<Type> queryTypeAndNumber();

    int queryAllCount();

    List<Type> queryAllTypePage(Pager typePager);
}
