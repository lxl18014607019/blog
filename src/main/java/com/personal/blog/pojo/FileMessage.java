package com.personal.blog.pojo;

import lombok.Data;
/**
* @description:
* @author: luxinlong
**/

@Data
public class FileMessage {
    private Long id;

    private String fileUrl;

    private String filePath;

    private Double fileSize;

}