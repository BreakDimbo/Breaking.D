package com.limbo.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by main on 7/2/16.
 */
public class HibernateOne2OneTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }


    @Test
    public void one2one() {
        Husband h = new Husband();
        Wife w = new Wife();

        h.setName("老公");
        h.setWife(w);

        w.setName("老婆");

        Session s = sessionFactory.getCurrentSession();
        s.beginTransaction();

        s.save(h);
        s.save(w);

        s.getTransaction().commit();

    }

}