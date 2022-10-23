package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.CodeMapper;
import com.personal.blog.pojo.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class CodeMapperImpl {
    @Autowired
    private CodeMapper codeMapper;

    public List<Code> selectByPrimaryKey(Long id){
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return codeMapper.selectList(queryWrapper);
    }

    public int deleteByPrimaryKey(Long id){
        return codeMapper.deleteById(id);
    }

    public int insert(Code code){
        return codeMapper.insert(code);
    }

    public int update(Code code){
        return codeMapper.update(code,null);
    }

    public Integer selectByCode(String  code){
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("security_code", code);
        String securityCode = codeMapper.selectOne(queryWrapper).getSecurityCode();
        return Integer.getInteger(securityCode);
    }

}

