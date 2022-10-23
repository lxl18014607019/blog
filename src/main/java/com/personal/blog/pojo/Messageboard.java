package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_messageboard")
public class Messageboard {
    private Long id;

    private String avatar;

    private String content;

    private String createTime;

    private String email;

    private String nickName;

    private Integer parentId;

    private boolean admin;

    private String parentName;

    private Boolean authorization;

    @TableField(exist = false)
    //子回复
    private List<Messageboard> sonMessage;

    @TableField(exist = false)
    //parent
    Messageboard parentMessage;


}