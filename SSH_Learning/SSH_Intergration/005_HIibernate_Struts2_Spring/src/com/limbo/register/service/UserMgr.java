package com.limbo.register.service;

import com.limbo.register.model.User;

/**
 * Created by Break.D on 7/8/16.
 */
public interface UserMgr {
    public boolean exists(User u);
    public void add(User u);
}
