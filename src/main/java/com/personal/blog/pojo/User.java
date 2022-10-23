package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_user")
public class User {
    private Long id;

    private String avatar;

    private Date creatTime;

    private String email;

    private String nickName;

    private String password;

    private Integer type;

    private Date updateTime;

    private String userName;

    private String description;

    private String pageTitle;

    private String cardTitle;

    private String firstPicture;

    private String theme;

    private Boolean state;

    @TableField(value = "search_url")
    private String searchBg;

}