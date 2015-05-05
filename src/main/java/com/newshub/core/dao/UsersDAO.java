package com.newshub.core.dao;

import com.newshub.core.domain.Users;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;


/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class UsersDAO implements DAO<Integer, Users> {
    public void create(Users entity) {
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

    public void update(Users entity) {
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
            Users users = get(id);
            session = new HibernateUtils().getSession();
            session.delete(users);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public Users get(Integer id) {
        Session session = null;
        Users users = null;
        try{
            session = new HibernateUtils().getSession();
            users = (Users) session.load(Users.class, id);
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return users;
    }
}
