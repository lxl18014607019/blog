package com.personal.blog.mapper.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.blog.mapper.BlogMapper;
import com.personal.blog.mapper.TypeMapper;
import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.SearchBlog;
import com.personal.blog.pojo.Type;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luxinlong
 * @Description:
 */
@Repository
public class BlogMapperImpl {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;


    public Blog selectByPrimaryKey(Long id){
        return blogMapper.selectById(id);
    }

    public int deleteByPrimaryKey(Long id){
        return blogMapper.deleteById(id);
    }

    public int updateByPrimaryKey(Blog blog){
        return blogMapper.updateById(blog);
    }

    public int insert(Blog blog){
        return blogMapper.insert(blog);
    }

    public List<Blog> selectAll(int start,int number){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        Page<Blog> page = new Page<>(start,number);
        queryWrapper.ne("recycle", "1");
        return blogMapper.selectPage(page,queryWrapper).getRecords();
    }



//    查询的字段去掉tagId ,nature,content,recycle,sort,password
    public List<Blog> getAllSimpleBlog(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", "1");
//        去掉部分字段
        queryWrapper.select(Blog.class,i->!i.getColumn().equals("tagId")&&
                !i.getColumn().equals("nature")  &&
                !i.getColumn().equals("content") &&
                !i.getColumn().equals("recycle") &&
                !i.getColumn().equals("sort") &&
                !i.getColumn().equals("password"));
        return blogMapper.selectList(queryWrapper);
    }

    public List<Type> selectType(){
        return typeMapper.selectList(null);
    }


    /*
     * @description:通过title和文章内容查询
     * @author: luxinlong
     * @param: searchTitle
     * @return: List<Blog>
     **/
    public List<Blog> selectBlogByTitle(String searchTitle) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.like("title", searchTitle)
                .or().like("description", searchTitle);

        return blogMapper.selectList(queryWrapper);
    }

    /*
     * @description:通过title查询并且扩大范围
     * @author: luxinlong
     * @param: searchTitle
     * @return: List<Blog>
     **/
    public List<Blog> selectBlogByTitleEnlargeRange(String searchTitle){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.like("title", searchTitle)
                .or().like("description", searchTitle)
                .or().like("content", searchTitle);

        return blogMapper.selectList(queryWrapper);
    }


    /*
     * @description:通过typeid查询 分页
     * @author: luxinlong
     * @param: null
     * @return: null
     **/

    public List<Blog> selectBlogByTypeId(Long typeId,Integer startPage,Integer id){
        int total = selectBlogCountByTypeId(id.longValue());
        Page<Blog> page = new Page<>(startPage,total);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.eq("type_id", typeId);
        List<Blog> blogs = blogMapper.selectPage(page, queryWrapper).getRecords();
        for(Blog blog:blogs){
            blog.setType(typeMapper.selectById(typeId));
        }
        return blogs;

    }

    /*
     * @description:通过typeid查询 不分页
     * @author: luxinlong
     * @param: typeId
     * @return: List<Blog>
     **/
    public List<Blog> selectBlogByTypeId(Long typeId){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.eq("type_id", typeId);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        for(Blog blog:blogs){
            blog.setType(typeMapper.selectById(typeId));
        }
        return blogs;

    }

    /*
     * @description:通过typeid计数
     * @author: luxinlong
     * @param: typeId
     * @return: int
     **/
    public int selectBlogCountByTypeId(Long typeId){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId).ne("recycle", 1);
        return blogMapper.selectCount(queryWrapper);
    }

    /*
     * @description:通过typeId查询博客，用于约束分类是否能被删除
     * @author: luxinlong
     * @param: typeId
     * @return: List<Long>
     **/
    public List<Blog> selectAllBlogByTypeId(Long typeId){
        new ArrayList<>();
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId);
        queryWrapper.select("id");
        return blogMapper.selectList(queryWrapper);
    }

    public List<Blog> selectBlogByRecommend(String recommend){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommend", recommend);
        queryWrapper.ne("recycle", 1);
        return blogMapper.selectList(queryWrapper);
    }

    public int selectBlogCount(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        return blogMapper.selectCount(queryWrapper);

    }

    public List<Blog> selectBlogOrderByCreateTime(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.orderByDesc("create_time");
        return blogMapper.selectList(queryWrapper);
    }

    public List<Blog> selectBlogOrderByCreateTimeLimit(int range){
        Page<Blog> page = new Page<>(0,range);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        queryWrapper.orderByDesc("create_time");
        return blogMapper.selectPage(page,queryWrapper).getRecords();
    }

//增加view
    public int addView(Long id){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog = new Blog();
        blog.setId(id);
        updateWrapper.setSql("view = view +1");
        return blogMapper.update(blog,updateWrapper);
    }

    //增加admire
    public int addAdmire(Long id){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog = new Blog();
        blog.setId(id);
        updateWrapper.setSql("admire = admire +1");
        return blogMapper.update(blog,updateWrapper);
    }

    //修改存在状态
    public int updateDeleteStateById(Long id){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<Blog>();
        Blog blog = new Blog();
        blog.setId(id);
        updateWrapper.set(true,"recycle", 1);
        return blogMapper.update(blog,updateWrapper);
    }

    public int updateRecoverStateById(Long id){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog = new Blog();
        blog.setId(id);
        updateWrapper.set(true,"recycle", 0);
        return blogMapper.update(blog,updateWrapper);
    }

    //查询出在回收站的博客
    public List<Blog> selectBlogByRecycle(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recycle", 1);
        return blogMapper.selectList(queryWrapper);
    }

//    批量删除
    public int  UpdateMultipleDeleteStateById(Long[] ids){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",ids);
        updateWrapper.set(true, "recycle", 1);
        return blogMapper.update(null,updateWrapper);
    }

//    记录生活查询
    public List<Blog> selectBlogByRecordLife(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1).eq("record_life", 1);
        queryWrapper.orderByDesc("create_time");
        return blogMapper.selectList(queryWrapper);

    }

//    回收站中进行查询
    public List<Blog> selectBlogByTitleFromRecycle(SearchBlog searchBlog){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("recycle", 1);
        String searchTitle = searchBlog.getSearchTitle();
        if(searchTitle!=null && !searchTitle.equals("")){
            queryWrapper.like("title", searchTitle);
        }
        String typeId = searchBlog.getSearchTypeId();
        if(typeId!=null && !typeId.equals("") && !typeId.equals("0")){
            queryWrapper.eq("typeId", typeId);
        }
        Boolean realSearchRecommend = searchBlog.getRealSearchRecommend();
        if(realSearchRecommend!=null){
            queryWrapper.eq("real_search_recommend", realSearchRecommend);
        }
        return blogMapper.selectList(queryWrapper);
    }

//    查询源文本内容
    public String selectBlogOriginalContent(Long id){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.select("content");
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        if(blogs.size()>0){
            return blogs.get(0).getContent();
        }else {
            return null;
        }
    }

//    查询博客是否在回收站
    public int selectBlogByRecycleId(Long id){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("recycle", 1);
        return blogMapper.selectCount(queryWrapper);
    }

//    通过id修改sort
    public int updateSortById(Long id,int sort){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog=new Blog();
        blog.setId(id);
        blog.setSort(sort);
        return blogMapper.update(blog,updateWrapper);
    }

    public List<Blog> selectAllBlogOnlySort(int startPage,int number){
        Page<Blog> page = new Page<>(startPage,number);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","sort","title");
        queryWrapper.ne("recycle",1);
        queryWrapper.orderByDesc("sort","create_time");
        return blogMapper.selectPage(page, queryWrapper).getRecords();
    }
//    查询文章是否加密
    public boolean selectBlogisEncrypted(Long id){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("password");
        queryWrapper.isNotNull("password");
        queryWrapper.eq("id",id);

        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        if(blogs.size()>0){
            Blog blog = blogs.get(0);
            return blog.getPassword() != null && !blog.getPassword().equals("");
        }else {
            return false;
        }
    }


    public Integer selectBlogEncryption(Long blogId,String code){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("id", blogId);
        queryWrapper.eq("password", code);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        if(blogs.size()>0){
            Blog blog = blogMapper.selectList(queryWrapper).get(0);
            return Math.toIntExact(blog.getId());
        }else{
            return null;
        }
    }

    public List<Blog> selectBlogByVarious(SearchBlog searchBlog){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recycle", 0);
        String searchTitle = searchBlog.getSearchTitle();
        if(searchTitle!=null && !searchTitle.equals("")){
            queryWrapper.like("title", searchTitle);
        }
        String searchTypeId = searchBlog.getSearchTypeId();
        if(searchTypeId!=null && !searchTypeId.equals("") &&!searchTypeId.equals("0")){
            queryWrapper.eq("typeId", searchTypeId);
        }
        Boolean realSearchRecommend = searchBlog.getRealSearchRecommend();
        if(realSearchRecommend!=null){
            queryWrapper.eq("recommend", realSearchRecommend);
        }
        return blogMapper.selectList(queryWrapper);
    }

    public Blog selectBlogRemoveContentByBlogId(Long blogId){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", blogId);
        queryWrapper.select(Blog.class,i->!i.getColumn().equals("content"));
        return blogMapper.selectOne(queryWrapper);
    }

    public int updateBlogPasswordByBlogId(Long blogId,String password){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog = new Blog();
        blog.setId(blogId);
        updateWrapper.set(true, "password", password);
        return blogMapper.update(blog,updateWrapper);
    }



//    public List<Blog> selectBlogBySql(String sql) {
//
//    }
}
















