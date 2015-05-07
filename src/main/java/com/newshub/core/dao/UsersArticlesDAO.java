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
    private Session session;

    public void create(UsersArticles entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }

    }

    public void update(UsersArticles entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }

    }

    public void delete(UsersArticlesPK id) {
        try{
            UsersArticles usersArticles = get(id);
            session.delete(usersArticles);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }

    }

    public UsersArticles get(UsersArticlesPK id) {
        UsersArticles usersArticles = null;
        try{
            usersArticles = (UsersArticles) session.load(UsersArticles.class, id);
        }
        catch (Exception e) {

        }
        return usersArticles;
    }

}
