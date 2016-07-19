package com.limbo.spring.proxy;

import com.limbo.spring.user.User;
import com.limbo.spring.user.UserMgr;
import com.limbo.spring.user.UserMgrImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Break.D on 7/6/16.
 */
public class ProxyTest {


    public static void main(String[] str) {
        UserMgr userMgr = new UserMgrImpl();
        User user = new User();

        InvocationHandler handler = new CheckUserHandler(userMgr);
        UserMgr u =(UserMgr) Proxy.newProxyInstance(userMgr.getClass().getClassLoader(), userMgr.getClass().getInterfaces(), handler);

        u.save(user);
    }
}
