package com.limbo.register.action;

import com.limbo.register.model.User;
import com.limbo.register.service.UserMgr;
import com.limbo.register.service.impl.UserMgrImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by Break.D on 7/8/16.
 */
public class UserAction extends ActionSupport {

    private User user;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    /*public String add() {
        if (user.exists()) return this.ERROR;
        else {
            UserMgr userMgr = new UserMgrImpl();
            userMgr.add(user);
            return this.SUCCESS;
        }
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
