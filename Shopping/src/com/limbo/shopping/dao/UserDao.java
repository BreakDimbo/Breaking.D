package com.limbo.shopping.dao;

import com.limbo.shopping.model.User;

import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public interface UserDao {
    public boolean checkUser(User u);
    public User checkUser(String username, String password);
    public void save(User u);
    public boolean delete(int id);
    public void update(User u);
    public User loadById(int id);
    public int getUsers(List<User> users, int pageNo, int page_size);
}
