package com.limbo.spring.service;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;

/**
 * Created by main on 7/4/16.
 */
public class UserService {

    UserDao userDao;

    public void save(User u) {
        userDao.save();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
