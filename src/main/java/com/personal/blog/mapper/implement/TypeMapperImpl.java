package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.blog.mapper.BlogMapper;
import com.personal.blog.mapper.TypeMapper;
import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class TypeMapperImpl {
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private  BlogMapperImpl blogDao;



    public Type selectByPrimaryKey(Long id){
        return typeMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return typeMapper.deleteById(id);
    }

    public int insert(Type type){
        return typeMapper.insert(type);
    }

    public int updateByPrimaryKey(Type type){
        UpdateWrapper<Type> updateWrapper = new UpdateWrapper<>();
        return typeMapper.update(type,updateWrapper);
    }

    public Type selectTypeByName(String name){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return typeMapper.selectOne(queryWrapper);
    }

    public List<Type> selectAllType(){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        return typeMapper.selectList(queryWrapper);
    }

    public List<Long> selectBlogIdByTypeId(Long typeId){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId);
        queryWrapper.ne("recycle", 1);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        List<Long> blogIds=new ArrayList<>();
        for(Blog blog:blogs){
            blogIds.add(blog.getId());
        }
        return blogIds;
    }

    public int selectAllCount(){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        return typeMapper.selectCount(queryWrapper);
    }

    public List<Type> selectTypeAndNumber(){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        List<Type> typeList = typeMapper.selectList(queryWrapper);
        for(Type type:typeList){
            type.setBlogList(blogDao.selectBlogByTypeId(type.getId()));
        }
        return typeList;
    }

    public List<Type> selectAllTypePage(int start,int number){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        Page<Type> page = new Page<>(start,number);
        return typeMapper.selectPage(page,queryWrapper).getRecords();
    }
}

