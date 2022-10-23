package com.personal.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.Type;
import com.personal.blog.pojo.User;
import com.personal.blog.service.BlogService;
import com.personal.blog.service.TypeService;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.BuildPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Controller
public class TypeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public String goType
            (@RequestParam(required = false) String id, @RequestParam(required = false) Integer startPage, Model model)
            throws JsonProcessingException {

        //默认局部刷新
        boolean first = false;
        //如果起始页为null,不再进行局部刷新,返回整个页面types,默认第1页
        if (startPage == null){
            startPage = 1;
            first = true;
        }

        //查询出所有类型
        List<Type> types = typeService.queryAllType();

        //如果id为null
        if (id == null){
            //查询出第一个博客的id
            id = types.get(0).getId() + "";
        }

        //查询出总条数
        int total = blogService.queryBlogCountByTypeId(Long.valueOf(id));

        //构建Pager
        Pager pager = BuildPager.biuldPager(total,startPage);
        //查询对应ID的名称
        Type type = typeService.queryTypeById(Long.valueOf(id));
        //查询id对应的博客
        List<Blog> blogs = blogService.queryBlogByTypeId(id,pager);
        //查询user
        User user = userService.queryUser();
        //查询Type,从缓存中取数据
        List<Type> typesAndBlogs = typeService.queryTypeAndNumber();

        //加入
        model.addAttribute("user",user);
        model.addAttribute("typesAndBlogs",typesAndBlogs);
        model.addAttribute("blogs",blogs);
        model.addAttribute("pager",pager);
        model.addAttribute("recommendBlog",blogService.queryBlogByRecommend(""));
        model.addAttribute("types",typeService.queryAllType());
        if (type != null){
            model.addAttribute("typeTitle",type.getName());
            model.addAttribute("typeId",type.getId());
        }
        return first ? "type" : "type :: typeFlushFragment";
    }

    @PostMapping("/pageType")
    public String pageType
            (@RequestParam(required = false) String id,
             @RequestParam(required = false) Integer startPage, RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("id",id);
        redirectAttributes.addAttribute("startPage",startPage);

//        return "redirect:/types?id=" + id + "&startPage=" + startPage;
        return "redirect:/types";
    }

}