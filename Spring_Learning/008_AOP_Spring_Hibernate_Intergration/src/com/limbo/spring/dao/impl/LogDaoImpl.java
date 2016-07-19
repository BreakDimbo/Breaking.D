package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.LogDao;
import com.limbo.spring.model.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Break.D on 7/7/16.
 */
@Component
public class LogDaoImpl implements LogDao{
    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void log(Log log) {
        Session session = sessionFactory.getCurrentSession();
        session.save(log);

//        throw new RuntimeException("exception");
    }

}
