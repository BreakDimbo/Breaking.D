package com.limbo.register.model;

import com.limbo.register.service.impl.UserMgrImpl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Break.D on 7/8/16.
 */
@Entity
public class User {
    private int id;
    private String username;
    private String password;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean exists() {
        return new UserMgrImpl().exists(this);
    }
}
