package com.limbo.register.service.impl;

import com.limbo.register.dao.UserDao;
import com.limbo.register.dao.impl.UserDaoImpl;
import com.limbo.register.model.User;
import com.limbo.register.service.UserMgr;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Break.D on 7/8/16.
 */
@Component("userManager")
public class UserMgrImpl implements UserMgr {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public boolean exists(User u) {
        return userDao.checkUserExists(u);

    }

    @Transactional
    public void add(User u) {
        userDao.save(u);
    }
}
