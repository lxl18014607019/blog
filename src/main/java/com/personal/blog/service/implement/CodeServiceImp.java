package com.personal.blog.service.implement;


import com.personal.blog.mapper.CodeMapper;
import com.personal.blog.mapper.implement.CodeMapperImpl;
import com.personal.blog.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @description:
* @author: luxinlong
**/

@Service
public class CodeServiceImp implements CodeService {

    @Autowired
    private CodeMapperImpl codeDao;


    @Override
    public Integer verificationCode(String code) {

        return codeDao.selectByCode(code);
    }
}
