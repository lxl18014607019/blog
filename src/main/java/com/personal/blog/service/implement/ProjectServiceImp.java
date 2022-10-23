package com.personal.blog.service.implement;


import com.personal.blog.mapper.implement.ProjectMapperImpl;
import com.personal.blog.pojo.Project;
import com.personal.blog.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class ProjectServiceImp implements ProjectService {

    @Autowired
    private ProjectMapperImpl projectDao;

    @Override
    public Project[] queryProjects() {
        List<Project> projectList = projectDao.selectProject();
        Project[] projects=new Project[projectList.size()];
        return projectList.toArray(projects);
    }
}
