package com.limbo.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by main on 7/2/16.
 */
public class HibernateCoreAPITest {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();

        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void close() {
        sessionFactory.close();
    }

    @Test
    public void testTeacherSave() {
        Teacher teacher = new Teacher();
        teacher.setName("李老师");
        teacher.setGender(Gender.FEMALE);
        teacher.setGood(true);
        teacher.setTitle("高级");
        teacher.setYourWifeName("Who knows");
        teacher.setBirthDate(new Date());

        /*
        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        session1.save(teacher);
        session1.getTransaction().commit();
        */

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        session2.save(teacher);
        session2.getTransaction().commit();
        session2.close();
    }

    @Test
    public void testTeacherSave3State() {
        Teacher teacher = new Teacher();
        teacher.setName("李老师");
        teacher.setTitle("高级");


        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        session1.save(teacher);
        System.out.println(teacher.getId());
        session1.getTransaction().commit();

    }

    @Test
    public void testGet() {

        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        Teacher t = (Teacher) session1.get(Teacher.class, 1);
        session1.getTransaction().commit();
        System.out.println(t.getName());

    }

    @Test
    public void testLoad() {

        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        Teacher t = (Teacher) session1.load(Teacher.class, 1);
        session1.getTransaction().commit();
        System.out.println(t.getName());

    }

    @Test
    public void testUpdate() {

        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        Teacher t = (Teacher) session1.load(Teacher.class, 1);

        t.setName("Update");

        session1.getTransaction().commit();

    }

    @Test
    public void testSaveOrUpdate() {

        Teacher teacher = new Teacher();
        teacher.setName("李老师");
        teacher.setGender(Gender.FEMALE);
        teacher.setGood(true);
        teacher.setTitle("高级");
        teacher.setYourWifeName("Who knows");
        teacher.setBirthDate(new Date());


        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        session1.saveOrUpdate(teacher);
        session1.getTransaction().commit();

        teacher.setName("阿达");

        Session session2 = sessionFactory.getCurrentSession();
        session2.beginTransaction();
        session2.saveOrUpdate(teacher);
        session2.getTransaction().commit();


    }

    @Test
    public void testClear() {

        Session session1 = sessionFactory.getCurrentSession();
        session1.beginTransaction();
        Teacher t1 = (Teacher) session1.load(Teacher.class, 2);
        System.out.println(t1.getName());

        session1.clear();

        Teacher t2 = (Teacher) session1.load(Teacher.class, 2);
        System.out.print(t2.getName());

        session1.getTransaction().commit();

    }

    @Test
    public void testFlush() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Teacher t = (Teacher)session.load(Teacher.class, 1);
        t.setName("tttt");


//        session.flush();

        t.setName("ttttt");


        session.getTransaction().commit();


    }
}