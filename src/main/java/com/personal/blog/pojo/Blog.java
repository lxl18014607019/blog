package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Objects;

@Data
@TableName(value = "t_blog")
public class Blog {
    private Long id;

    private String firstPicture;

    private String createTime;

    private String updateTime;

    private Integer typeId;

    private Integer tagId;

    private String nature;

    private String title;

    private Integer view;

    private String description;

    private Boolean stick;

    private Boolean recommend;

    private Boolean reprint;

    private Boolean supportReview;

    private Integer admire;

    private String content;

    private Boolean recycle;

    private Boolean recordLife;

    private Integer sort;

    private String password;

    private boolean switchMarkdown;

    @TableField(exist = false)
    //Type
    private Type type;

}