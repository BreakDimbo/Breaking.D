package com.limbo.register.dao.impl;

import com.limbo.register.dao.UserDao;
import com.limbo.register.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Break.D on 7/8/16.
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {

    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public void save(User u) {
        hibernateTemplate.save(u);
    }

    @Override
    public void delete(User u) {
        hibernateTemplate.delete(u);
    }

    @Override
    public void update(User u) {
        hibernateTemplate.update(u);
    }

    @Override
    public boolean checkUserExists(User u) {
        List<User> users = (List<User>) hibernateTemplate.find("from User u where u.username = '" + u.getUsername() + "'");


        if(users != null && users.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User loadById(int id) {
        User u = hibernateTemplate.get(User.class, id);
        return u;
    }
}
