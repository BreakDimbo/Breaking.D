package com.limbo.register.dao.impl;

import com.limbo.register.dao.UserDao;
import com.limbo.register.model.User;
import com.limbo.register.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Break.D on 7/8/16.
 */
public class UserDaoImpl implements UserDao {

    private static SessionFactory sessionFactory;

    static {
        sessionFactory = Util.setUp();
    }


    @Override
    public void save(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
    }

    @Override
    public void delete(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(u);
        session.getTransaction().commit();
    }

    @Override
    public void update(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(u);
        session.getTransaction().commit();
    }

    @Override
    public boolean checkUserExists(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Long count = (Long) session.createQuery("select count(*) from User u where u.username = :username")
                .setString("username", u.getUsername())
                .uniqueResult();
        session.getTransaction().commit();

        if (count > 0) {
            return true;
        }

        return false;
    }

    @Override
    public User loadById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User u = (User) session.get(User.class, id);
        session.getTransaction().commit();
        return u;
    }
}
