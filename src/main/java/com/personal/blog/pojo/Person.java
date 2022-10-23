package com.personal.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
* @description:
* @author: luxinlong
**/

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private int age;


    //move
    public void move(){
        System.out.println("人在走...");
    }

}
