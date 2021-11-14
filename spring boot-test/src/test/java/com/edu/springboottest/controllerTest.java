package com.edu.springboottest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJunitApplication.class)
public class controllerTest {
    @Test
    public void controller(){
        System.out.println("11111");
    }

}
