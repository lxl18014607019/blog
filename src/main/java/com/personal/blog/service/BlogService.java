package com.personal.blog.service;

import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.SearchBlog;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface BlogService {

    //插入blog接口
    Integer setBlogInsert(Blog blog);

    List<Blog> getAllBlog(Pager pager);

    List<Blog> getAllBlog();

    Blog getBlogByIdAndConvert(Long id);

    Blog getBlogById(Long id);

    Integer setBlogUpdate(Blog blog);

    int deleteBlogById(Long id);

    List queryBlogByTitle(String searchtitle,boolean enlargeRange);

    List<Blog> queryBlogByTypeId(String searchtypeid,Pager pager);

    List<Blog> queryBlogByTypeId(String searchtypeid);

    List<Blog> queryBlogByRecommend(String searchrecommend);

    int queryBlogCount();

    List<Blog> queryBlogOrderByCreateTime();

    int setBlogUpdateNoDefault(Blog blog);

    int changeDeleteStateById(Long id);

    List<Blog> queryBlogByrecycle();

    int queryBlogByrecycle(Long id);

    int modifyBlogRecover(Long id);

    int queryBlogCountByTypeId(Long id);

    List<Blog> queryBlogOrderByCreateTimeLimit(Integer range);

//    List<Blog> queryBlogBySql(String sql);

    int deleteMultipleBlogById(Long[] ids);

    List<Blog> queryBlogByRecordLife();

    List<Blog> queryBlogByTitleFromRecycle(SearchBlog searh);

    String queryBlogOriginalContent(Integer id);

    int modifySortById(Integer id,Integer sort);

    List<Blog> queryAllBlogOnlySort(Pager pager);

    List<Blog> queryAllBlogByTypeId(String id);

    int addAdmire(Long blogid);

    Boolean queryBlogIsEncrypted(String id);

    Integer queryBlogEncryption(String code,Long blogId);

    List<Blog> queryBlogByVarious(SearchBlog search);

    Blog queryBlogRemoveContentByBlogId(Long blogid);

    int modifyBlogPasswordByBlogID(Long blogid, String password);
}
