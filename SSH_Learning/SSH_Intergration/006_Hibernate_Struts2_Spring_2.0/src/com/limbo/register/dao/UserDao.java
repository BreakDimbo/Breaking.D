package com.limbo.register.dao;

import com.limbo.register.model.User;

import java.util.List;

/**
 * Created by Break.D on 7/8/16.
 */
public interface UserDao {
    void save(User u);

    void delete(User u);

    void update(User u);

    boolean checkUserExists(User u);

    User loadById(int id);

    List<User> getUsers();
}
