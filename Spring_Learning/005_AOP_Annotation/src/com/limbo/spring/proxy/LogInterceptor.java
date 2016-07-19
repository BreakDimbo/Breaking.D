package com.limbo.spring.proxy;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Break.D on 7/6/16.
 */
@Aspect
@Component
public class LogInterceptor {
    @Pointcut("execution(public * com.limbo.spring.service.UserService.add(..))")
    public void myMethod(){};

    @Before("myMethod()")
    public void before() {
        System.out.println("Logging the error...");
    }

}
