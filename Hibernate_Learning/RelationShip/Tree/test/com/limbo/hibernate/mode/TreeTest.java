package com.limbo.hibernate.mode;

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
public class TreeTest {

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

    public void testSave() {

        Org o = new Org();
        o.setName("总公司");
        Org o1 = new Org();
        o1.setName("分公司01");
        Org o1_1 = new Org();
        o1_1.setName("分公司01_01");
        Org o2 = new Org();
        o2.setName("分公司02");
        Org o2_1 = new Org();
        o2_1.setName("分公司02_01");
        Org o2_1_1 = new Org();
        o2_1_1.setName("分公司02_01_01");


        o.getChildren().add(o1);
        o.getChildren().add(o2);
        o1.getChildren().add(o1_1);
        o2.getChildren().add(o2_1);
        o2_1.getChildren().add(o2_1_1);

        o1.setParent(o);
        o2.setParent(o);
        o1_1.setParent(o1);
        o2_1.setParent(o2);
        o2_1_1.setParent(o2_1);

        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(o);

        session.getTransaction().commit();
    }

    @Test
    public void testTree() {
        testSave();

        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Org o = (Org) session.load(Org.class, 1);
        print(o, 0);

        session.getTransaction().commit();


    }

    private void print(Org o, int level) {

        StringBuffer preString = new StringBuffer();

        for (int i = 0; i < level; i++) {
            preString.append("-|-");
        }

        System.out.println(preString + o.getName());

        level++;
        for(Org os : o.getChildren()) {
            print(os, level);
        }
    }
}