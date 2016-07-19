package com.limbo.register.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Break.D on 7/8/16.
 */
public class Util {



    public static SessionFactory setUp() {
        Configuration config = new Configuration();
        config.configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        return config.buildSessionFactory(serviceRegistry);
    }
}
