package com.limbo.shopping.service;

import com.limbo.shopping.dao.UserDao;
import com.limbo.shopping.dao.impl.UserDaoImpl;
import com.limbo.shopping.model.User;

import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public class UserMgr {
    private static UserDao userDao = new UserDaoImpl();
    private static UserMgr userMgr;

    private UserMgr() {
        super();
    }

    public static UserMgr getInstance() {
        if (userMgr == null) {
            userMgr = new UserMgr();
        }
        return userMgr;
    }

    public boolean exists(User u) {
        return userDao.checkUser(u);
    }

    public User loginVal(String username, String password) {
        return userDao.checkUser(username, password);
    }

    public void addUser(User u) {
        userDao.save(u);
    }

    public int getUsers(List<User> users, int pageNo, int page_size) {
        return userDao.getUsers(users, pageNo, page_size);
    }

    public boolean delete(int id) {
        return userDao.delete(id);
    }

    public void update(User u) {
        userDao.update(u);
    }
}
