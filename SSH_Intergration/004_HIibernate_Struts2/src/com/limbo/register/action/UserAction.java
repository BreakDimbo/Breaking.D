package com.limbo.register.action;

import com.limbo.register.model.User;
import com.limbo.register.service.UserMgr;
import com.limbo.register.service.impl.UserMgrImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Break.D on 7/8/16.
 */
public class UserAction extends ActionSupport {


    private  UserMgr userMgr = new UserMgrImpl();

    private String username;
    private String password1;
    private String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String execute() {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password1);
        if (user.exists()) return "fail";
        else {
            userMgr.add(user);
            return SUCCESS;
        }
    }

}
