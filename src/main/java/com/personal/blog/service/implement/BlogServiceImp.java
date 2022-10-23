package com.personal.blog.service.implement;

import com.personal.blog.mapper.BlogMapper;
import com.personal.blog.mapper.implement.BlogMapperImpl;
import com.personal.blog.pojo.Blog;
import com.personal.blog.pojo.Pager;
import com.personal.blog.pojo.SearchBlog;
import com.personal.blog.service.BlogService;
import com.personal.blog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    private BlogMapperImpl blogDao;

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer setBlogInsert(Blog blog) {

        //给博客插入创建时间和更新时间
        if (blog != null){
            blog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            blog.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date()));
            //是否在回收站的状态为假
            blog.setRecycle(false);
            //没选中默认值
            this.defaultvalue(blog);
            blog.setView(0);
        }

        //调用dao层插入
        Integer count = blogDao.insert(blog);

        return count;
    }

    //查询出所有的博客
    @Override
    public List<Blog> getAllBlog(Pager pager) {

        //分页查询
        List<Blog> blogs = blogDao.selectAll((pager.getStartPage() - 1) * pager.getDataNumber(),pager.getDataNumber());

        return blogs;
    }

    public List<Blog> getAllBlog() {

        List<Blog> blogs = blogDao.getAllSimpleBlog();

        return blogs;
    }


    /*直接把content转换成html*/
    @Override
    public Blog getBlogByIdAndConvert(Long id) {

        //修改view
        blogDao.addView(id);
        //查询blog
        Blog blog = blogDao.selectByPrimaryKey(id);
        //设置view
        blog.setView(blog.getView());

        //转换成html
        if (blog.isSwitchMarkdown()){
            blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        }

        return blog;
    }

    /*以markdown的格式content*/
    @Override
    public Blog getBlogById(Long id) {
        //查询blog
        Blog blog = blogDao.selectByPrimaryKey(id);

        return blog;
    }

    //修改博客
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer setBlogUpdate(Blog blog) {

        //给博客插入创建时间和更新时间
        if (blog != null){
            blog.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //没选中默认值
            defaultvalue(blog);
        }

        int count = blogDao.updateByPrimaryKey(blog);

        return count;
    }

    private Blog defaultvalue(Blog blog){
        if (blog.getRecommend() == null)
            blog.setRecommend(false);
        if (blog.getReprint() == null)
            blog.setReprint(false);
        if (blog.getStick() == null)
            blog.setStick(false);
        if (blog.getSupportReview() == null)
            blog.setSupportReview(false);
        if (blog.getAdmire() == null)
            blog.setAdmire(0);
        if (blog.getRecordLife() == null)
            blog.setRecordLife(false);
        return blog;
    }

    //删除博客通过id
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int deleteBlogById(Long id) {

        int count = blogDao.deleteByPrimaryKey(id);

        return count;
    }

    //通过title查询blog
    @Override
    public List queryBlogByTitle(String searchtitle,boolean enlargeRange) {

        //创建list集合
        List<Blog> blogs= null;

        //查询
        if (enlargeRange)
            blogs = blogDao.selectBlogByTitleEnlargeRange(searchtitle);
        else
            blogs = blogDao.selectBlogByTitle(searchtitle);

        return blogs;
    }

    //通过typeid查询
    @Override
    public List<Blog> queryBlogByTypeId(String searchTypeId,Pager pager) {

        //查询
        List<Blog> blogs = blogDao.selectBlogByTypeId(Long.valueOf(searchTypeId),(pager.getStartPage() - 1) * pager.getDataNumber(),pager.getDataNumber());

        return blogs;
    }

    @Override
    public List<Blog> queryBlogByTypeId(String searchTypeId) {
        //查询
        List<Blog> blogs = blogDao.selectBlogByTypeId(Long.valueOf(searchTypeId));
        return blogs;
    }

    @Override
    public List<Blog> queryAllBlogByTypeId(String searchTypeId) {


        return blogDao.selectAllBlogByTypeId(Long.valueOf(searchTypeId));
    }

    @Override
    public int addAdmire(Long blogid) {

        return blogDao.addAdmire(blogid);
    }

    @Override
    public Boolean queryBlogIsEncrypted(String id) {

        return blogDao.selectBlogisEncrypted(Long.valueOf(id));

    }

    @Override
    public Integer queryBlogEncryption(String code,Long blogId) {

        return blogDao.selectBlogEncryption(blogId,code);
    }

    @Override
    public List<Blog> queryBlogByVarious(SearchBlog search) {

        return blogDao.selectBlogByVarious(search);
    }

    /**
     * 查询博客不要content
     * @param blogid
     * @return
     */
    @Override
    public Blog queryBlogRemoveContentByBlogId(Long blogid) {

        return blogDao.selectBlogRemoveContentByBlogId(blogid);
    }

    /**
     * 加密博客 通过blogid
     * @param blogid
     * @param password
     * @return
     */
    @Override
    public int modifyBlogPasswordByBlogID(Long blogid, String password) {

        return blogDao.updateBlogPasswordByBlogId(blogid,password);
    }


    //通过recommend查询
    @Override
    public List<Blog> queryBlogByRecommend(String searchRecommend) {

        //查询
        List<Blog> blogs = blogDao.selectBlogByRecommend(searchRecommend);

        return blogs;
    }

    //查询博客总数
    @Override
    public int queryBlogCount() {
        int count = blogDao.selectBlogCount();
        return count;
    }

    //查询通过创建日期排序
    @Override
    public List<Blog> queryBlogOrderByCreateTime() {

        //查询
        List<Blog> blogs = blogDao.selectBlogOrderByCreateTime();

        return blogs;
    }

    //编辑博客的保存功能
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int setBlogUpdateNoDefault(Blog blog) {

        //修改
        int count = blogDao.updateByPrimaryKey(blog);

        return count;
    }

    //放入回收站
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int changeDeleteStateById(Long id) {
        //修改存在状态
        int count = blogDao.updateDeleteStateById(id);
        return count;
    }

    //跳转到回收站,查询出博客
    @Override
    public List<Blog> queryBlogByrecycle() {

        //查询出在回收站的博客
        List<Blog> blogs = blogDao.selectBlogByRecycle();

        return blogs;
    }

    @Override
    public int queryBlogByrecycle(Long id) {
        return blogDao.selectBlogByRecycleId(id);
    }

    //恢复博客
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modifyBlogRecover(Long id) {

        return blogDao.updateRecoverStateById(id);
    }

    /*根据typeid查询博客数量,用于分类页面分页*/
    @Override
    public int queryBlogCountByTypeId(Long id) {

        return blogDao.selectBlogCountByTypeId(id);
    }

    /*限定范围查询*/
    @Override
    public List<Blog> queryBlogOrderByCreateTimeLimit(Integer range) {
        return blogDao.selectBlogOrderByCreateTimeLimit(range);
    }

//    //自定义查询
//    @Override
//    public List<Blog> queryBlogBySql(String sql) {
//        return blogDao.selectBlogBySql(sql);
//    }

    /*批量删除博客*/
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public int deleteMultipleBlogById(Long[] ids) {
        return blogDao.UpdateMultipleDeleteStateById(ids);
    }

    /*记录生活*/
    @Override
    public List<Blog> queryBlogByRecordLife() {

        //blogs
        List<Blog> blogs = blogDao.selectBlogByRecordLife();

        for (Blog blog : blogs) {
            blog.setCreateTime(blog.getCreateTime().replace('-',' '));
        }

        return blogs;
    }

    /*查询回收站*/
    public List queryBlogByTitleFromRecycle(SearchBlog search){
        return blogDao.selectBlogByTitleFromRecycle(search);
    }

    @Override
    public String queryBlogOriginalContent(Integer id) {

        return blogDao.selectBlogOriginalContent(id.longValue());
    }

    @Override
    public int modifySortById(Integer id,Integer sort) {

        return blogDao.updateSortById(id.longValue(),sort);
    }

    @Override
    public List<Blog> queryAllBlogOnlySort(Pager pager) {
        //分页查询
        List<Blog> blogs = blogDao.selectAllBlogOnlySort((pager.getStartPage() - 1) * pager.getDataNumber(),pager.getDataNumber());
        return blogs;
    }

}