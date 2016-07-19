package com.limbo.register.service.impl;

import com.limbo.register.dao.UserDao;
import com.limbo.register.dao.impl.UserDaoImpl;
import com.limbo.register.model.User;
import com.limbo.register.service.UserMgr;

/**
 * Created by Break.D on 7/8/16.
 */
public class UserMgrImpl implements UserMgr {

    private UserDao userDao = new UserDaoImpl();

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean exists(User u) {
        return userDao.checkUserExists(u);

    }

    public void add(User u) {
        userDao.save(u);
    }
}
