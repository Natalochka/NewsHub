package com.newshub.core.dao;

import com.newshub.core.domain.Articles;
import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 4/30/2015.
 */
public class ArticleTagsDAO implements DAO<ArticlesTagsPK, ArticlesTags> {

    public void create(ArticlesTags entity) {
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

    public void update(ArticlesTags entity) {
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

    public void delete(ArticlesTagsPK id) {
        Session session = null;
        try{
            ArticlesTags article = get(id);
            session = new HibernateUtils().getSession();
            session.delete(article);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public ArticlesTags get(ArticlesTagsPK id) {
        Session session = null;
        ArticlesTags articlesTags = null;
        try{
            session = new HibernateUtils().getSession();
            articlesTags = (ArticlesTags) session.load(ArticlesTags.class, id);
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return articlesTags;
    }
}
