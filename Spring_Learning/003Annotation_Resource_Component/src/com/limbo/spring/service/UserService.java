package com.limbo.spring.service;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by main on 7/4/16.
 */
@Component("userService")
public class UserService {

    UserDao userDao;

    public void save(User u) {
        userDao.save();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
