package com.personal.blog.controller.admin;


import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.Type;
import com.personal.blog.service.BlogService;
import com.personal.blog.service.TypeService;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.BuildPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName:TypeController
 * Package:com.myblog.blog.controller.admin
 * Description:
 *
 * @Date:2020/7/25 17:07
 * @com.chuangmei
 */
@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    //跳转至type管理页面
    @GetMapping("/types")
    public String type(Model model){

        //查询出所有的Type
        List<Type> types = typeService.queryAllType();

        //加入
        model.addAttribute("types",types);

        return "admin/types";
    }

    /*跳转至添加页面*/
    @GetMapping("/typeinput")
    public String typesinput(){
        return "admin/type-input";
    }

    /*新增Type*/
    @PostMapping("/addtype")
    public String addType(HttpServletRequest request, Model model) throws InterruptedException {

        //拿到typename
        String name = request.getParameter("typename");

        //封装Type
        Type type = new Type();
        type.setName(name);

        //调用typeService进行插入
        int count = typeService.setType(type);

        return count > 0 ? "redirect:/admin/typespage" : "error/error";
    }

    //检查Type是否存在
    @GetMapping("/typerepetition")
    public @ResponseBody
    String checkType(String typename){

        //调用service层查询
        int count = typeService.queryTypeBynameCount(typename);

        //调试
        //System.out.println("count:" + count);

        return "" + count;
    }

    //跳转到编辑分类页面
    @GetMapping("/typeeditor/{id}")
    public String editorType(@PathVariable String id,Model model){

        //通过id查询类型
        Type type = typeService.queryTypeById(Long.valueOf(id));

        //加入
        model.addAttribute("type",type);

        return "admin/type-update";
    }

    /*修改分类*/
    @PostMapping("/updatetype")
    public String updateType(Type type) throws InterruptedException {

        int count = typeService.updateTypeByType(type);

        return count > 0 ? "redirect:/admin/typespage" : "error/error";
    }

    //删除分类
    @GetMapping("/typedelete/{id}")
    public String deleteType(@PathVariable String id, Model model) throws InterruptedException {

        // 查询分类是否已被博客引用 , 如果被引用不能删除
        List<Blog> blogs = blogService.queryAllBlogByTypeId(id);

        if (blogs.size() > 0) {

            model.addAttribute("message","当前分类被其他博客所引用,不能删除,请先将当前分类下的博客指向其它分类!!");

            model.addAttribute("user",userService.queryUser());

            return "error/error";

        }else {
            //删除
            int count = typeService.deleteTypeById(Long.valueOf(id));


            return count > 0 ? "redirect:/admin/typespage" : "error/error";
        }


    }

    /*分页查询*/
    @GetMapping("/typespage")
    public String typePage(@RequestParam(required = false) Integer startPage,Model model){

        //起始页
        boolean firstpage = false;
        if (startPage == null){
            startPage = 1;
            firstpage = true;
        }

        //查询出typecount
        int typeCount = typeService.queryAllCount();

        //构建Pageer
        Pager typePager = BuildPager.biuldPager(typeCount,startPage,10);

        /*分页查询*/
        List<Type> types = typeService.queryAllTypePage(typePager);

        model.addAttribute("types",types);
        model.addAttribute("pager",typePager);

        return firstpage ? "admin/types" : "admin/types : typesfragment";
    }

    /*分页查询*/
    @PostMapping("/typepage")
    public String typePagePost(@RequestParam("startPage") Integer startPage,Model model){


        //起始页
        if (startPage == null) startPage = 1;

        //查询出typecount
        int typeCount = typeService.queryAllCount();

        //构建Pageer
        Pager typePager = BuildPager.biuldPager(typeCount,startPage,10);

        /*分页查询*/
        List<Type> types = typeService.queryAllTypePage(typePager);

        model.addAttribute("types",types);
        model.addAttribute("pager",typePager);

        return "admin/types :: typesfragment";
    }

}
