package com.limbo.spring.user;

/**
 * Created by Break.D on 7/6/16.
 */
public class UserMgrImpl implements UserMgr {

    @Override
    public void save(User u) {
        System.out.println("saving user...");
        u.eat();
        System.out.println("saving is over.");
    }
}
