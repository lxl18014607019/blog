package com.personal.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* @description:
* @author: luxinlong
**/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_project")
public class Project {
    private Long id;

    private String pPictureUrl;

    private String pDescription;

    private String pLinkUrl;

    private String pTitle;

    private String pTag;

    private String pOnlineLink;

}