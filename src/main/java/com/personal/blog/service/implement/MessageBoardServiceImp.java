package com.personal.blog.service.implement;


import com.personal.blog.mapper.MessageboardMapper;
import com.personal.blog.mapper.UserMapper;
import com.personal.blog.mapper.implement.MessageboardMapperImpl;
import com.personal.blog.mapper.implement.UserMapperImpl;
import com.personal.blog.pojo.Messageboard;
import com.personal.blog.service.MessageBoardService;
import com.personal.blog.utils.MarkdownUtils;
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
public class MessageBoardServiceImp implements MessageBoardService {

    //Dao
    @Autowired
    private MessageboardMapperImpl messageboardDao;

    @Autowired
    private UserMapperImpl userDao;

    //插入留言
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addMessage(Messageboard messageboard) {

        //添加插入日期
        messageboard.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if (null == messageboard.getParentId()){
            messageboard.setParentId(0);
        }
        //调用dao层进行插入数据
        int count = messageboardDao.insert(messageboard);

        return count;
    }

    /*查询所有 , 不分父子*/


    /**
     * 查询留言
     * @return
     */
    @Override
    public List<Messageboard> queryMessageBoard() {

        //messageboardList
        List<Messageboard> messageboardList = messageboardDao.selectMessageBoard();

        //特殊字符处理queryMessageBoard
        emojiAndMarkdownConver(messageboardList);

        //构建父级评论信息
        buildCommentName(messageboardList);

        messageboardList = eachComment(messageboardList);
        return messageboardList;
    }

    @Override
    public List<Messageboard> queryMessageAllBoard() {

        return messageboardDao.selectAllMessageBoard();
    }

    // 删除
    @Override
    public int deleteMessage(Long id) {

        return messageboardDao.deleteByPrimaryKey(id);
    }

    /**
     * 授权是否显示评论
     * @param id
     * @return
     */
    @Override
    public int changeAuthorizationByMessageID(Long id) {

        return messageboardDao.updateAuthorizationByMessageID(id);
    }

    /**
     * 后台查询全部留言
     * @return
     */
    @Override
    public List<Messageboard> queryMessageBoardAll() {
        return messageboardDao.selectAllMessageBoard();
    }

    /**
     * 遍历每一个评论,进行转换
     * @param messageboardList
     */
    private void emojiAndMarkdownConver(List<Messageboard> messageboardList){

        for (Messageboard message : messageboardList){
            message.setContent(MarkdownUtils.markdownToHtmlExtensions(EmojiParser.parseToUnicode(message.getContent())));

            //如果有子评论
            if (message.getSonMessage().size() > 0){
                //遍历子评论解析表情
                for (Messageboard soncomment : message.getSonMessage()){
                    soncomment.setContent(MarkdownUtils.markdownToHtmlExtensions(EmojiParser.parseToUnicode(soncomment.getContent())));
                    //如果还有子评论,继续递归
                    if (soncomment.getSonMessage().size() > 0)
                        emojiAndMarkdownConver(soncomment.getSonMessage());
                }
            }
        }
    }

    //构建评论
    private void buildCommentName(List<Messageboard> comments){
        //迭代,如果有子评论,那么将此评论放入createName进行创建父级评论
        for(Messageboard comment : comments){
            if (comment.getSonMessage().size() > 0){
                creatName(comment);
            }
        }
    }

    private void creatName(Messageboard comment){
        //迭代comment集合,如果有子评论
        if (comment.getSonMessage().size() > 0){
            //遍历子评论
            for (Messageboard sonComment : comment.getSonMessage()){
                //创建父级评论
                Messageboard parent = new Messageboard();
                parent.setNickName(comment.getNickName());
                parent.setId(comment.getId());
                //将父级评论存入子评论
                sonComment.setParentMessage(parent);
                //如果子评论中还有子评论,那麽递归
                if (sonComment.getSonMessage().size() > 0){
                    creatName(sonComment);
                }
            }
        }
    }

    private List<Messageboard> eachComment(List<Messageboard> comments) {
        List<Messageboard> commentsView = new ArrayList<>();
        for (Messageboard comment : comments) {
            commentsView.add(comment);
        }
        combineChildren(commentsView);
        return commentsView;
    }


    private void combineChildren(List<Messageboard> comments) {

        for (Messageboard comment : comments) {
            List<Messageboard> sonComment = comment.getSonMessage();
            for(Messageboard soncomment : sonComment) {
                able = true;
                recursively(soncomment);
            }
            comment.setSonMessage(saveComment);
            //清除临时存放区
            saveComment = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子级的集合
    private List<Messageboard> saveComment = new ArrayList<>();

    private boolean able = true;

    /*
        递归迭代
     */
    private void recursively(Messageboard comment) {

        if (able){
            saveComment.add(comment);
        }
        if (comment.getSonMessage().size()>0) {
            List<Messageboard> replys = comment.getSonMessage();
            for (Messageboard reply : replys) {
                saveComment.add(reply);
                if (reply.getSonMessage().size()>0) {
                    able = false;
                    recursively(reply);

                }
            }
        }
    }

}
