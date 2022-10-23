package com.personal.blog.utils;

import com.personal.blog.pojo.BlogComment;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public class EmojiUtility {

  public static String convertEmojiToStr(String source){
      return EmojiParser.parseToAliases(source);
  }

  public static void convertStrToEmoji(List<BlogComment> comments){
      for (BlogComment comment : comments){
          comment.setContent(EmojiParser.parseToUnicode(comment.getContent()));
      }
  }

}
