package com.limbo.spring.proxy;

import com.limbo.spring.user.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Break.D on 7/6/16.
 */
public class CheckUserHandler implements InvocationHandler {

    Object target;

    public CheckUserHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("check the username");

        method.invoke(target, args);

        System.out.println("check pass");

        return null;

    }
}
