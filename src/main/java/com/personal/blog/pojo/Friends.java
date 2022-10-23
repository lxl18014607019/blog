package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_friends")
public class Friends {
    private Long id;

    private String url;

    private String title;

    private String picture;

    private Boolean authorization;


}