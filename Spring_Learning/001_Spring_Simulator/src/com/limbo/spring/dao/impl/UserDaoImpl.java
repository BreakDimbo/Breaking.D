package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.UserDao;

/**
 * Created by main on 7/4/16.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.print("User has been saved");
    }
}
