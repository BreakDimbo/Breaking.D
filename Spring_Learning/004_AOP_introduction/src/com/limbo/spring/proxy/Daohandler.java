package com.limbo.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Break.D on 7/6/16.
 */
public class Daohandler implements InvocationHandler {

    public Object target;

    public Daohandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("logging the error...");
        method.invoke(target, args);
        System.out.println("logging is over...");
        return null;
    }
}
