package com.newshub.core.dao;

import com.newshub.core.domain.Articles;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Integer, Articles>{

    public void create (Articles entity) {
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

    public void update (Articles entity){
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

    public void delete (Integer id){
        Session session = null;
        try{
            Articles article = get(id);
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

    public Articles get (Integer id){
        Session session = null;
        Articles articles = null;
        try{
            session = new HibernateUtils().getSession();
            articles = (Articles) session.load(Articles.class, id);
        }
        catch (Exception e) {
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return articles;
    }
}