package com.personal.blog.service.implement;

import com.personal.blog.mapper.implement.BlogCommentMapperImpl;
import com.personal.blog.pojo.BlogComment;
import com.personal.blog.service.BlogCommentService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Service
public class BlogCommentServiceImp implements BlogCommentService {

    @Autowired
    private BlogCommentMapperImpl blogCommentDao;

    //存放迭代找出的所有子级的集合
    private List<BlogComment> saveComment = new ArrayList<>();

    //是否添加到saveComment(存储子评论的)
    private boolean able;

    //发布评论
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addBlogComment(BlogComment blogComment) {

        //设置父级id
        if (blogComment.getParentId() == null){
            blogComment.setParentId(Long.valueOf(0));
        }
        //设置创建时间
        blogComment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //设置头像

        //插入评论
        int count = blogCommentDao.insert(blogComment);

        return count;
    }

    //id查询评论
    @Override
    public List<BlogComment> queryCommentsByBlogId(Long id) {

        //查询
        List<BlogComment> comments = blogCommentDao.selectCommentsByBlogId(id);

        //表情处理
        EmojiConver(comments);

        //创建父级评论
        BuildCommentName(comments);

        return eachComment(comments);
    }

    @Override
    public int queryCommentCount() {
        int count = blogCommentDao.selectCommentsCount();
        return count;
    }

    /**
     * 限制数量
     * @param blogid
     * @param count
     * @return
     */
    @Override
    public List<BlogComment> queryCommentsByBlogIdLimit(Long blogid, Integer count) {

        //查询
        List<BlogComment> comments = blogCommentDao.selectCommentsByBlogId(blogid,count);

        //表情处理
        EmojiConver(comments);

        //创建父级评论
        BuildCommentName(comments);

        return eachComment(comments);

    }

    @Override
    public int queryCommentCountByBlogId(Long blogid) {
        return blogCommentDao.selectCommentsCountByBlogId(blogid);
    }

    @Override
    public BlogComment queryCommentByCommentId(Long id) {

        return blogCommentDao.selectCommentsByCommentId(id);
    }

    private void EmojiConver(List<BlogComment> comments){
        //特殊字符处理
        for (BlogComment comment : comments){
            comment.setContent(EmojiParser.parseToUnicode(comment.getContent()));
            //如果有子评论
            if (comment.getSonComment()!=null && comment.getSonComment().size() > 0){
                //遍历子评论解析表情
                for (BlogComment soncomment : comment.getSonComment()){
                    soncomment.setContent(EmojiParser.parseToUnicode(soncomment.getContent()));
                    //递归
                    if (soncomment.getSonComment()!=null && soncomment.getSonComment().size() > 0)
                        EmojiConver(soncomment.getSonComment());
                }
            }
        }
    }

    //构建评论
    private void BuildCommentName(List<BlogComment> comments){
        //迭代,如果有子评论,那么将此评论放入createName进行创建父级评论
        for(BlogComment comment : comments){
            if (comment.getSonComment()!=null && comment.getSonComment().size() > 0){
                creatName(comment);
            }
        }
    }

    private void creatName(BlogComment comment){
        //迭代comment集合,如果有子评论
        if (comment.getSonComment()!=null && comment.getSonComment().size() > 0){
            //遍历子评论
            for (BlogComment sonComment : comment.getSonComment()){
                //创建父级评论
                BlogComment parent = new BlogComment();
                parent.setNickName(comment.getNickName());
                parent.setId(comment.getId());
                //将父级评论存入子评论
                sonComment.setParentComment(parent);
                //如果子评论中还有子评论,那麽递归
                if (sonComment.getSonComment().size() > 0){
                    creatName(sonComment);
                }
            }
        }
    }

    private List<BlogComment> eachComment(List<BlogComment> comments) {
        List<BlogComment> commentsView = new ArrayList<>(comments);

        combineChildren(commentsView);
        return commentsView;
    }

    /*
     * @description:将评论的所有子评论
     * @author: luxinlong
     * @param: comments
     * @return: void
     **/
    private void combineChildren(List<BlogComment> comments) {

        for (BlogComment comment : comments) {
            List<BlogComment> sonComment = comment.getSonComment();
            for(BlogComment soncomment : sonComment) {
                able = true;
                recursively(soncomment);
            }
            comment.setSonComment(saveComment);
            //清除临时存放区
            saveComment = new ArrayList<>();
        }
    }



    /*
     * @description:递归迭代
     * @author: luxinlong
     * @param: comment
     * @return: void
     **/
    private void recursively(BlogComment comment) {
        if (able) {
            saveComment.add(comment);
        }
        if (comment.getSonComment().size()>0) {
            List<BlogComment> replys = comment.getSonComment();
            for (BlogComment reply : replys) {
                saveComment.add(reply);
                if (reply.getSonComment().size()>0) {
                    able = false;
                    // 标记 able为false , 避免reply被重复加入
                    recursively(reply);
                }
            }
        }
    }
}
