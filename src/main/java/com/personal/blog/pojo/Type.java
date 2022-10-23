package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_type")
public class Type implements Serializable {
    private Long id;

    private String name;

    @TableField(exist = false)
    //对应的博客
    private List<Blog> blogList = new ArrayList<>();

}