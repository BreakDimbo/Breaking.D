package com.limbo.spring.spring;

import com.limbo.spring.model.User;
import com.limbo.spring.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by main on 7/4/16.
 */
public class ClassPathXmlApplicationContextTest {
    @Test
    public void getBeanTest() throws Exception {

        ApplicationContext bf = new ClassPathXmlApplicationContext("beans.xml");

        UserService userService = (UserService) bf.getBean("userService");

        User u1 = new User();
        u1.setName("小明");
        u1.setPassword("12345");

        userService.save(u1);
    }

}