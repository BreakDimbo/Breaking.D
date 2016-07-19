package com.limbo.shopping.dao.impl;

import com.limbo.shopping.dao.UserDao;
import com.limbo.shopping.model.User;
import org.junit.Assert;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/11/16.
 */
public class UserDaoImplTest {

    private UserDao userDao = new UserDaoImpl();

    @org.junit.Test
    public void checkUser() throws Exception {
        User u = new User();
        u.setUsername("小明");


        boolean exists = userDao.checkUser(u);
        Assert.assertEquals(true, exists);
    }

    @org.junit.Test
    public void save() throws Exception {
        User u = new User();
        u.setUsername("小明");
        u.setPassword("12345");
        u.setAddr("天河路");
        u.setPhone("1324567876");
        u.setRdate(new Date());

        userDao.save(u);

    }

    @org.junit.Test
    public void delete() throws Exception {
        userDao.delete(2);

    }

    @org.junit.Test
    public void update() throws Exception {

    }

    @org.junit.Test
    public void loadById() throws Exception {

    }

}