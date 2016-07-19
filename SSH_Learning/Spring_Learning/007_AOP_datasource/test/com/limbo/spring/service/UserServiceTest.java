package com.limbo.spring.service;

import com.limbo.spring.model.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/6/16.
 */
public class UserServiceTest {


    @Test
    public void firstTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        UserService userService = (UserService) context.getBean("userService");

        User u = new User();
        u.setName("wanger");
        userService.add(u);

        System.out.println(userService.getClass());

    }




}