package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @description:
* @author: luxinlong
**/
@Data
@TableName(value = "t_background")
public class Background {
    private Long id;

    private String imgUrl;
}