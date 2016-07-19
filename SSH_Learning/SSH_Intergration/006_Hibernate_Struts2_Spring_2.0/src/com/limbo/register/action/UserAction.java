package com.limbo.register.action;

import com.limbo.register.model.User;
import com.limbo.register.service.UserMgr;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Break.D on 7/8/16.
 */
//@Component("user")
//@Scope("prototype")
public class UserAction extends ActionSupport {


    private UserMgr userMgr;

    private List<User> users;

    private String username;
    private String password1;
    private String password2;

    public UserMgr getUserMgr() {
        return userMgr;
    }

    @Resource(name = "userManager")
    public void setUserMgr(UserMgr userMgr) {
        this.userMgr = userMgr;
    }

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String list() {
        users = userMgr.getUsers();

        return "list";
    }

    public String execute() {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password1);
        if (userMgr.exists(user)) return "fail";
        else {
            userMgr.add(user);
            return SUCCESS;
        }
    }

}
