package com.personal.blog;

import com.personal.blog.pojo.Background;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class BlogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testBackground(){
        Background background = new Background();
        background.setId(123L);
        System.out.println(background);
    }

    @Test
    void testDBConnection(){

    }

}
