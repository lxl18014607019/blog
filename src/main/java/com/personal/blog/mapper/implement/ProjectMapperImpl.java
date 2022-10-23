package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.personal.blog.mapper.ProjectMapper;
import com.personal.blog.pojo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class ProjectMapperImpl {

    @Autowired
    private ProjectMapper projectMapper;

    public Project selectByPrimaryKey(Long id){
        return projectMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return projectMapper.deleteById(id);
    }

    public int insert(Project project){
        return projectMapper.insert(project);
    }

    public int updateByPrimaryKey(Project project){
        UpdateWrapper<Project> updateWrapper = new UpdateWrapper<>();
        return projectMapper.update(project,updateWrapper);
    }

    public List<Project> selectProject(){
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        return projectMapper.selectList(queryWrapper);
    }
}

