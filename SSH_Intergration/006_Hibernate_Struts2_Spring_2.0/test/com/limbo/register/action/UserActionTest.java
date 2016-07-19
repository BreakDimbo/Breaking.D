package com.limbo.register.action;

import com.limbo.register.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/9/16.
 */
public class UserActionTest {
    @Test
    public void execute() throws Exception {
        UserAction userAction = new UserAction();

        User u = new User();
        u.setUsername("lllll");
        u.setPassword("ooo");

        userAction.getUserMgr().add(u);
        userAction.getUserMgr().exists(u);
    }

    @Test
    public void userListTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserAction userAction = (UserAction) context.getBean("user");
        userAction.list();
        Assert.assertTrue(userAction.getUsers().size() > 0);
    }

}