package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.UserDao;
import com.limbo.spring.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by Break.D on 7/6/16.
 */
@Component
public class UserDaoImpl implements UserDao {

    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User u) {

            Session session = sessionFactory.getCurrentSession();
            session.save(u);

    }
}
