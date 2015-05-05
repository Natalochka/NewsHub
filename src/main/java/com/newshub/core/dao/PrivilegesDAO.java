package com.newshub.core.dao;

import com.newshub.core.domain.Privileges;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class PrivilegesDAO implements DAO<Integer, Privileges>{
    public void create(Privileges entity) {
        Session session = null;
        try {
            session = new HibernateUtils().getSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public void update(Privileges entity) {
        Session session = null;
        try {
            session = new HibernateUtils().getSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public void delete(Integer id) {
        Session session = null;
        try{
            Privileges privileges = get(id);
            session = new HibernateUtils().getSession();
            session.delete(privileges);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public Privileges get(Integer id) {
        Session session = null;
        Privileges privileges = null;
        try{
            session = new HibernateUtils().getSession();
            privileges = (Privileges) session.load(Privileges.class, id);
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return privileges;
    }
}
