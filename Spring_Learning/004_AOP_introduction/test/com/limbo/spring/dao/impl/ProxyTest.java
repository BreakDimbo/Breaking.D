package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.proxy.Daohandler;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/6/16.
 */
public class ProxyTest {

    @Test
    public void proxyTest() {

        UserDao userDao = new UserDaoImpl();

        InvocationHandler handler = new Daohandler(userDao);

        UserDao u = (UserDao) Proxy.newProxyInstance(userDao.getClass().getClassLoader(), userDao.getClass().getInterfaces(), handler);

        u.save();
    }

}