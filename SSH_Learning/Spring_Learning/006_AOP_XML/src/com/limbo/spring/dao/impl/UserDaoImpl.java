package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Break.D on 7/6/16.
 */
@Component
public class UserDaoImpl implements UserDao {
    @Override
    public void save(User u) {
        System.out.print("User saved");
    }
}
