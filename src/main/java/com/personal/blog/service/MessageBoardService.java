package com.personal.blog.service;



import com.personal.blog.pojo.Messageboard;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public interface MessageBoardService {

    int addMessage(Messageboard messageboard);

    List<Messageboard> queryMessageBoard();
    List<Messageboard> queryMessageAllBoard();

    int deleteMessage(Long id);

    int changeAuthorizationByMessageID(Long id);

    List<Messageboard> queryMessageBoardAll();
}
