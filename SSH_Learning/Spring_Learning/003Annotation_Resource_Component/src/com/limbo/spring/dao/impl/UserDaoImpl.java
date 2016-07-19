package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.UserDao;
import org.springframework.stereotype.Component;

/**
 * Created by main on 7/4/16.
 */
@Component("userDaoImpl")
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.print("User has been saved");
    }
}
