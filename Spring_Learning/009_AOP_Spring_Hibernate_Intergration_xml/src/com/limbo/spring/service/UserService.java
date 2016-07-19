package com.limbo.spring.service;

import com.limbo.spring.dao.LogDao;
import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.Log;
import com.limbo.spring.model.User;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Break.D on 7/6/16.
 */
@Component

public class UserService {

    UserDao userDao;
    LogDao logDao;

    public LogDao getLogDao() {
        return logDao;
    }

    @Resource
    public void setLogDao(LogDao logDao) {
        this.logDao = logDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void add(User user) {

        userDao.save(user);
        Log log = new Log();
        log.setMesg("save user" + user.getName());
        logDao.save(log);
    }
}
