package com.newshub.core.dao;

import com.newshub.core.domain.Privileges;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class PrivilegesDAO implements DAO<Integer, Privileges>{
    private Session session;

    public void create(Privileges entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void update(Privileges entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }

    }

    public void delete(Integer id) {
        try{
            Privileges privileges = get(id);
            session.delete(privileges);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }

    }

    public Privileges get(Integer id) {
        Privileges privileges = null;
        try{
            privileges = (Privileges) session.load(Privileges.class, id);
        }
        catch (Exception e) {

        }
        return privileges;
    }
}
