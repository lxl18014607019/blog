package com.personal.blog.pojo;

import lombok.Data;

import java.util.Objects;

/**
* @description:
* @author: luxinlong
**/


@Data
public class SearchBlog {

    private String searchTitle;
    private String searchTypeId;
    private String searchRecommend;
    private Boolean realSearchRecommend;
    private String recognize = "default";

}
