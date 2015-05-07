package com.newshub.core.dao;

import com.newshub.core.domain.UsersArticles;
import com.newshub.core.domain.UsersArticlesPK;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
public class UsersArticlesDAO implements DAO<UsersArticlesPK, UsersArticles> {
    private Session session;

    public UsersArticlesDAO(Session session) {
        this.session = session;
    }

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

    public List<UsersArticles> getAll() {
        return new ArrayList<UsersArticles>() {
            {
                UsersArticles usersArticles = null;
                try {
                    addAll(session.createCriteria(UsersArticles.class).list());
                } catch (Exception e) {
                }
            }
        };
    }
}
