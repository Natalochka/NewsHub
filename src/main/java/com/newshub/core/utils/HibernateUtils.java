package com.newshub.core.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

/**
 * Created by Natalie on 17.04.2015.
 */
public class HibernateUtils {
    private static final SessionFactory sessionFactory;
    // private static final ServiceRegistry serviceRegistry;
    private static Configuration configuration;
    private static StandardServiceRegistry serviceRegistry;
    private Session session;
    private Transaction transaction;
    
    public HibernateUtils (){
        session = sessionFactory.openSession();
        Locale.setDefault(Locale.ENGLISH);

    }

    static {
        configuration = new Configuration();
        configuration.configure();
        Configuration configuration = new Configuration().configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
    }

    public Session getSession() {
        return session;
    }
    
}
