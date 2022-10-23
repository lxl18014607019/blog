package com.personal.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @description:
* @author: luxinlong
**/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager {

    //每页展示条数,默认5
    private Integer dataNumber = 5;

    private Integer totalPage;

    private Integer startPage;

    private Integer pageQuantity;

    private List<Integer> pages;

}
