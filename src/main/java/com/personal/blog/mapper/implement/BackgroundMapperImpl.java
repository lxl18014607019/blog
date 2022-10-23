package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.blog.mapper.BackgroundMapper;
import com.personal.blog.pojo.Background;
import com.personal.blog.pojo.Pager;
import com.personal.blog.utils.BuildPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class BackgroundMapperImpl  {
    @Autowired
    private BackgroundMapper backgroundMapper;


    public int countByUrl(String url) {
        QueryWrapper<Background> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("url", url);
        return backgroundMapper.selectCount(queryWrapper);
    }

    public int countAll(){
        return backgroundMapper.selectCount(null);
    }
//    public int insertSelective(Background background) {
//        return backgroudMapper.insert(background);
//    }

    public int insert(Background background){
        return backgroundMapper.insert(background);
    }

    public int updateById(Background background){
        return backgroundMapper.updateById(background);
    }

    public List<Background> selectByUrl(String  url){
        QueryWrapper<Background> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("url",url);
        return backgroundMapper.selectList(queryWrapper);
    }

    public List<Background> selectAll(){
        QueryWrapper<Background> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return backgroundMapper.selectList(queryWrapper);
    }


    public Page<Background> selectAll(int count){
        QueryWrapper<Background> queryWrapper=new QueryWrapper<>();
        Page<Background> page=new Page<>(0,count);

        queryWrapper.orderByAsc("id");
        return backgroundMapper.selectPage(page,queryWrapper);
    }

    public int deleteById(Long id ){
        return backgroundMapper.deleteById(id);
    }






}

