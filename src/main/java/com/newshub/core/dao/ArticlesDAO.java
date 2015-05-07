package com.newshub.core.dao;

import com.newshub.core.domain.Articles;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Integer, Articles>{
    private Session session;

    public ArticlesDAO(Session session) {
        this.session = session;
    }

    public void create (Articles entity) {
        Session session = null;
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
        }     }

    public void update (Articles entity){
        Session session = null;
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
        }     }

    public void delete (Integer id){
        Session session = null;
        try{
            Articles article = get(id);
            session.delete(article);
            session.getTransaction().commit();
        }
        catch (Exception e) {
        }
    }

    public Articles get (Integer id){
        Session session = null;
        Articles articles = null;
        try{
            articles = (Articles) session.load(Articles.class, id);
        }
        catch (Exception e) {
        }
        return articles;
    }

    public List<Articles> getAll() {
        return new ArrayList<Articles>() {
            {
                Articles articles = null;
                try {;
                    addAll(session.createCriteria(Articles.class).list());
                } catch (Exception e) {
                }
            }
        };
    }
}