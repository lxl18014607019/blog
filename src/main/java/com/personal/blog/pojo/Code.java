package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
* @description:
* @author: luxinlong
**/

@Data
@TableName(value = "t_code")
public class Code {
    private Long id;

    private String securityCode;

}