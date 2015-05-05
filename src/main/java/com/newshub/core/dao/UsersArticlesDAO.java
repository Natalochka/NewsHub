package com.newshub.core.dao;

import com.newshub.core.domain.Users;
import com.newshub.core.domain.UsersArticles;
import com.newshub.core.domain.UsersArticlesPK;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
public class UsersArticlesDAO implements DAO<UsersArticlesPK, UsersArticles> {
    public void create(UsersArticles entity) {
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

    public void update(UsersArticles entity) {
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

    public void delete(UsersArticlesPK id) {
        Session session = null;
        try{
            UsersArticles usersArticles = get(id);
            session = new HibernateUtils().getSession();
            session.delete(usersArticles);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public UsersArticles get(UsersArticlesPK id) {
        Session session = null;
        UsersArticles usersArticles = null;
        try{
            session = new HibernateUtils().getSession();
            usersArticles = (UsersArticles) session.load(UsersArticles.class, id);
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return usersArticles;
    }

}
