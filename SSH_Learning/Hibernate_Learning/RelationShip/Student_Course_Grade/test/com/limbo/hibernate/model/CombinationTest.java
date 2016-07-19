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
public class CombinationTest {

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
    public void test() {

        Student s = new Student();
        s.setName("小明");
        Course c = new Course();
        c.setName("语文");
        Score score = new Score();
        score.setCourse(c);
        score.setStudent(s);
        score.setScore(90);

        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(s);
        session.save(c);
        session.save(score);

        session.getTransaction().commit();
    }
}