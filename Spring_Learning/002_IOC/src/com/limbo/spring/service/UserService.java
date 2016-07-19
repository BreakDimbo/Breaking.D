package com.limbo.spring.service;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

    @Autowired
    public void setUserDao(@Qualifier("udi") UserDao userDao) {
        this.userDao = userDao;
    }
}
