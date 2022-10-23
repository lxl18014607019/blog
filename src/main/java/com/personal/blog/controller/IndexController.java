package com.personal.blog.controller;


import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.Type;
import com.personal.blog.pojo.User;
import com.personal.blog.service.BlogCommentService;
import com.personal.blog.service.BlogService;
import com.personal.blog.service.TypeService;
import com.personal.blog.service.UserService;
import com.personal.blog.utils.BuildPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * ClassName:IndexController
 * Package:com.myblog.blog.controller
 * Description:
 *\
 * @Date:2020/7/21 19:36
 * @com.chuangmei
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1") Integer startPage, HttpServletRequest request) {

        //查询出博客数量
        Integer blogcount = blogService.queryBlogCount();

        //构建pager , 使用默认页数
        Pager pager = BuildPager.biuldPager(blogcount, startPage);

        //查询出所有博客
        List<Blog> blogs = blogService.getAllBlog(pager);

        for(Blog blog:blogs){
            blog.setType(typeService.queryTypeById(Long.valueOf(blog.getTypeId())));
        }

        //查询出User
        User user = userService.queryUser();

        //查询出所有的类型
        List<Type> types = typeService.queryAllType();

        //查询出推荐博客
        List<Blog> recommendBlog = blogService.queryBlogByRecommend("");

        int commentcount = blogCommentService.queryCommentCount();

        //加入
        model.addAttribute("blogs", blogs);
        model.addAttribute("pager", pager);
        model.addAttribute("user", user);
        model.addAttribute("recommendBlog", recommendBlog);
        model.addAttribute("commentCount", commentcount);
        model.addAttribute("types", types);

        return "index";
    }


    @GetMapping("/mypage")
    public String page() {
        return "MyPage";
    }

    @GetMapping("/newpage")
    public String page(Model model) {

        User user = userService.queryUser();

        System.out.println(user.getSearchBg());

        model.addAttribute("user", user);
        return "newpage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        //user
        User user = userService.queryUser();
        model.addAttribute("user", user);
        return "about";
    }

    @GetMapping("/more")
    public String more(Model model){

        model.addAttribute("user",userService.queryUser());

        return "more";
    }

    @GetMapping("/friends")
    public String friends(Model model){

        model.addAttribute("user",userService.queryUser());

        return "friends";
    }

    @PostMapping("/theme")
    @ResponseBody
    public int theme(@RequestParam("theme") String theme, Model model) {

        //修改
        int success = userService.updateUserTheme(theme);
        System.out.println(theme);

        System.out.println(success);
        //model
        model.addAttribute("success", success);
        model.addAttribute("user", userService.queryUser());

        /*return "newpage";*/
        /*return "newpage :: suggestionFragment";*/
        return success;
    }

    @GetMapping("/getImg")
    @ResponseBody
    public void getImage(String imgname ,HttpServletResponse response) {
        String filePath = "D:\\电脑壁纸\\高清\\" + imgname;
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            FileInputStream fis = null;
            OutputStream os = null;
            try {
                fis = new FileInputStream(imageFile);
                os = response.getOutputStream();
                int count = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                    os.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}