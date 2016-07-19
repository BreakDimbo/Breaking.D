package com.limbo.register.action;

import com.limbo.register.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/9/16.
 */
public class UserActionTest {

    @Test
    public void test() {

        UserAction userAction = new UserAction();

        User u = new User();
        u.setUsername("lllll");
        u.setPassword("ooo");

        userAction.getUserMgr().add(u);
        userAction.getUserMgr().exists(u);
    }



}