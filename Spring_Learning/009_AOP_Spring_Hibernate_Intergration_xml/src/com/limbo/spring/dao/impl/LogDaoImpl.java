package com.limbo.spring.dao.impl;

import com.limbo.spring.dao.LogDao;
import com.limbo.spring.model.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Break.D on 7/7/16.
 */
@Component
public class LogDaoImpl implements LogDao{

    HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public void save(Log log) {
        hibernateTemplate.save(log);
    }

}
