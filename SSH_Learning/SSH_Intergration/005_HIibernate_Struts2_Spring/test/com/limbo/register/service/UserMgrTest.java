package com.limbo.register.service;

import com.limbo.register.model.User;
import com.limbo.register.service.impl.UserMgrImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Break.D on 7/8/16.
 */
public class UserMgrTest {
    @Test
    public void exists() throws Exception {

        User u = new User();
        u.setUsername("qqq");
        boolean exists = u.exists();
        Assert.assertEquals(true, exists);

    }

    @Test
    public void add() throws Exception {

        UserMgr userMgr = new UserMgrImpl();
        User user = new User();
        user.setUsername("6789");
        user.setPassword("123");

        boolean exists = user.exists();

        if (!exists) {
            userMgr.add(user);
            exists = user.exists();
            Assert.assertEquals(true, exists);
        } else {
            Assert.fail("not saved");
        }
    }

}