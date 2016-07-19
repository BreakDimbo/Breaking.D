package com.limbo.spring.service;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Break.D on 7/6/16.
 */
@Component
public class UserService {

    UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) {
        userDao.save(user);
    }
}
