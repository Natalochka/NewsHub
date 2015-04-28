package com.newshub.core.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

/**
 * Created by Natalie on 17.04.2015.
 */
public class HibernateUtils {
    private static final SessionFactory sessionFactory;
    // private static final ServiceRegistry serviceRegistry;
    private static Configuration configuration;
    private Session session;
    private Transaction transaction;
    
    public HibernateUtils (){
        session = sessionFactory.openSession();
        Locale.setDefault(Locale.ENGLISH);

    }

    static {
        configuration = new Configuration();
        configuration.configure();

        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return session;
    }
    
}
