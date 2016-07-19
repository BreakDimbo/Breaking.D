package com.limbo.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by main on 7/3/16.
 */
public class CRUDTest {

    private static SessionFactory sf;

    @BeforeClass
    public static void setUp() {
        Configuration cfg = new Configuration();
        cfg.configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void close() {
        sf.close();
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setName("u1");
        Group group = new Group();
        group.setName("g1");
        user.setGroup(group);


        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
    }

    @Test
    public void testSaveGroup() {
        User u1 = new User();
        u1.setName("u1");
        User u2 = new User();
        u2.setName("u2");
        Group group = new Group();
        group.setName("g1");
        group.getUsers().add(u1);
        group.getUsers().add(u2);
        u1.setGroup(group);
        u2.setGroup(group);


        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(group);

        session.getTransaction().commit();
    }

    @Test
    public void testRead() {
        testSaveGroup();

        Session session = sf.getCurrentSession();
        session.beginTransaction();

        User u = (User) session.get(User.class, 1);
        System.out.print(u.getName());

        /*Group g = (Group) session.get(Group.class, 1);

        for(User u : g.getUsers()) {
            System.out.print(u.getName() + "--");
        }*/

        session.getTransaction().commit();
    }

    @Test
    public void testDelete() {
        testSaveGroup();

        Session session = sf.getCurrentSession();
        session.beginTransaction();

       /* User u = (User) session.load(User.class, 1);
        u.setGroup(null);
        session.delete(u);*/

        Group g = (Group) session.get(Group.class, 1);
        g.setUsers(null);
        session.delete(g);

        session.getTransaction().commit();
    }

}