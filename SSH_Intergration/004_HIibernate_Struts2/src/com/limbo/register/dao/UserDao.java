package com.limbo.register.dao;

import com.limbo.register.model.User;

/**
 * Created by Break.D on 7/8/16.
 */
public interface UserDao {
    public void save(User u);
    public void delete(User u);
    public void update(User u);
    public boolean checkUserExists(User u);
    public User loadById(int id);
}
