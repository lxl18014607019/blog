package com.personal.blog.controller.admin;

import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.SearchBlog;
import com.personal.blog.pojo.Type;
import com.personal.blog.service.BlogService;
import com.personal.blog.service.TypeService;
import com.personal.blog.utils.BuildPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @description:
* @author: luxinlong
* @date: 2022/10/12
**/


@Controller
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    //博客管理页面每页显示的条数
    private static final int PAGE_NUMBER = 15;

    /*新增博客*/
    @GetMapping("/bloginput")
    public String input(Model model){

        //查询所有的Type
        List<Type> types = queryAllType();

        //加入blog和type
        model.addAttribute("blogs",new Blog());
        model.addAttribute("types",types);

        return "admin/blogs-input";
    }

    /*编辑博客*/
    @GetMapping("/bloginput/{id}")
    public String editorBlog(@PathVariable String id, Model model){

        //查询博客
        Blog blogs = blogService.getBlogById(Long.valueOf(id));

        //查询所有的Type
        List<Type> types = queryAllType();

        //加入要修改的博客的id,和blog
        model.addAttribute("blogs",blogs);
        model.addAttribute("types",types);

        return "admin/blogs-input";

    }

    //查询所有的type
    private List<Type> queryAllType() {

        List<Type> types = typeService.queryAllType();

        return types;
    }

    @GetMapping("/blogs")
    public String blog(@RequestParam(required = false) Integer startPage, Model model){


        if (startPage == null){
            startPage = 1;
        }

        //查询出总条数
        int total = blogService.queryBlogCount();

        //创建pager
        Pager pager = BuildPager.biuldPager(total,startPage,PAGE_NUMBER);

        //查询所有的博客
        List<Blog> blogs = blogService.getAllBlog(pager);

        //查询出所有的type
        List<Type> types = typeService.queryAllType();

        //加入
        model.addAttribute("blogs",blogs);
        model.addAttribute("types",types);
        model.addAttribute("pager",pager);
        return "admin/blogs";
    }

    @PostMapping("/blog")
    public String inputBlog(HttpServletRequest request,Blog blog){

        //count
        Integer count;

        if (blog.getId() == null){  //新增
            //调用service层插入blog
            count = blogService.setBlogInsert(blog);
        }else{  //修改
            count = blogService.setBlogUpdate(blog);
        }

        return count > 0 ? "redirect:/admin/blogs" : "error/error";
    }

    //删除博客
    @GetMapping("/deleteblog/{id}")
    public String deleteBlog(@PathVariable Long id){

        int count = blogService.changeDeleteStateById(id);

        return count > 0 ? "redirect:/admin/blogs" : "error/error";
    }


    @GetMapping("/searchblog")
    public String searchBlog(SearchBlog search, Model model){

        String result = "admin/blogs";

        //测试
        //System.out.println(search);

        List<Blog> blogs = null;

        //返回位置
        if ("batchOperation".equals(search.getRecognize()))
            result = "admin/batchOperation";
        else if ("recycle".equals(search.getRecognize()))
            result = "admin/recyclestand";

        if ("0".equals(search.getSearchRecommend())) {
            search.setRealSearchRecommend(null);
        }else if ("1".equals(search.getSearchRecommend())){
            search.setRealSearchRecommend(true);
        }else if ("-1".equals(search.getSearchRecommend())){
            search.setRealSearchRecommend(false);
        }

        /*  
            在回收站中查询
            实现此功能,需要重写sql语句,以下sql语句查询的是没有删除的博客,而此处需要查询已经删除的博客
            已实现
         */
        if ("recycle".equals(search.getRecognize())){
            blogs = blogService.queryBlogByTitleFromRecycle(search);
        }else { //不是回收站的查询, 以下实现方式比较笨, 后续应该修改为mybatis动态标签
            if (search.getSearchTitle() != null && !search.getSearchTitle().equals("") || search.getSearchTypeId() != null && !search.getSearchTypeId().equals("0") || search.getRealSearchRecommend() != null){
                //通过各种查询
                blogs = blogService.queryBlogByVarious(search);
                //blogs = blogService.queryBlogByTitle(search.getSearchtitle(),false);
            }/*else if(search.getSearchtypeid() != null && !search.getSearchtypeid().equals("0")){
                //通过typeid查询
                blogs = blogService.queryBlogByTypeId(search.getSearchtypeid());
            }else if(search.getSearchrecommend() != null){
                //通过推荐查询
                blogs = blogService.queryBlogByRecommend(search.getSearchrecommend());
            }*/else{
                //查询全部
                //如果是默认才进行分页
                if ("default".equals(search.getRecognize())){
                    Pager pager = BuildPager.biuldPager(blogService.queryBlogCount(),1,PAGE_NUMBER);
                    blogs = blogService.getAllBlog(pager);
                    model.addAttribute("pager",pager);
                }else {
                    blogs = blogService.getAllBlog();
                }
            }
        }

        //加入
        model.addAttribute("blogs",blogs);
        model.addAttribute("types",typeService.queryAllType());

        return result;
    }

    //保存博客
    @RequestMapping(value = "/savecontent", method = RequestMethod.POST)
    public @ResponseBody String saveContent(@RequestParam("id") Long id,@RequestParam("content") String content){

        //判断
        if (id == null || content == null || "".equals(content)){
            return "error";
        }

        //创建blog
        Blog blog = new Blog();
        blog.setId(id);
        blog.setContent(content);

        //修改
        int count = blogService.setBlogUpdateNoDefault(blog);

        return count > 0 ? "success" : "error";
    }

    /*跳转到回收站*/
    @GetMapping("/recyclebin")
    public String recycle(Model model){

        List<Blog> blogs = blogService.queryBlogByrecycle();

        List<Type> types = typeService.queryAllType();

        //加入
        model.addAttribute("blogs",blogs);
        model.addAttribute("types",types);


        return "admin/recyclestand";
    }

    /*彻底删除博客*/
    @PostMapping("/thoroughdeleteblog")
    public String thoroughDeleteblog(@RequestParam("id") Long id,Model model){

        //删除
        int count = blogService.deleteBlogById(id);

        //查询blogs
        model.addAttribute("blogs",blogService.queryBlogByrecycle());

        return count > 0 ? "admin/recyclestand :: recycletbody" : "error/error";
    }

    /*恢复*/
    @GetMapping("/recoverblog/{id}")
    public String recoverBlog(@PathVariable Long id){

        //恢复
        int count = blogService.modifyBlogRecover(id);

        return count > 0 ? "redirect:/admin/recyclebin" : "error/error";
    }

    @PostMapping("/blogspage")
    public String blogsPage(Integer pageStart,Model model){

        //查询出总条数
        int total = blogService.queryBlogCount();

        //构建pager
        Pager pager = BuildPager.biuldPager(total,pageStart,PAGE_NUMBER);

        //查询所有的博客
        List<Blog> blogs = blogService.getAllBlog(pager);

        //加入
        model.addAttribute("blogs",blogs);
        model.addAttribute("pager",pager);
        return "admin/blogs :: blogsListblogfragment";
    }

//    //自定义查询
//    @PostMapping("/customsearch")
//    public String customSearch(String sql,Model model){
//        //查询
//        List<Blog> blogs = blogService.queryBlogBySql(sql);
//
//        //type
//        List<Type> types = typeService.queryAllType();
//
//        //request
//        model.addAttribute("blogs",blogs);
//        model.addAttribute("types",types);
//
//        return "admin/blogs";
//    }

    @GetMapping("/batchOperation")
    public String batchOperationIndex(Model model){

        model.addAttribute("blogs", blogService.getAllBlog());
        model.addAttribute("types",typeService.queryAllType());
        return "admin/batchOperation";
    }

//    /*删除多个博客*/
//    @PostMapping("/blog/delmultiple")
//    @ResponseBody
//    public Three delMultiple(@RequestParam("ids") Long[] ids, Model model){
//
//        //批量删除
//        int count = blogService.deleteMultipleBlogById(ids);
//
//        if (count == ids.length)
//            return Three.NORMAL;
//        else if (count == 0)
//            return Three.ERROR;
//        else
//            return Three.MISSING;
//
//    }

    /**
     * 博客排序
     */
    @RequestMapping("/blog/sortadmin")
    public String sortAdmin(Model model){

        model.addAttribute("blogs",getBlogs());

        return "admin/blog-sort-admin";
    }

    /**
     * 修改 Sort
     */
    @PostMapping("/blog/updateSort")
    public String updateSort(@RequestParam Integer id,@RequestParam Integer sort, Model model){

        // 修改Sort
        int count = blogService.modifySortById(id,sort);

        model.addAttribute("blogs",getBlogs());

        return "admin/blog-sort-admin.html :: Sort";
    }

    private List getBlogs(){
        //博客数量
        Integer blogCount = blogService.queryBlogCount();

        // pager
        Pager pager = BuildPager.biuldPager(blogCount,1,blogCount);

        List<Blog> blogs = blogService.queryAllBlogOnlySort(pager);

        return blogs;
    }

}
