package com.newshub.core.dao;

import com.newshub.core.domain.Articles;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Integer, Articles>{

    private Session session;

    public void create (Articles entity) {
        try {
            //session = new HibernateUtils().getSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
        } /*finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }*/
    }

    public void update (Articles entity){

        try {

            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
        }
    }

    public void delete (Integer id){

        try{
            Articles article = get(id);
            session.delete(article);
            session.getTransaction().commit();
        }
        catch (Exception e) {
        }

    }

    public Articles get (Integer id){
        Articles articles = null;
        try{
            articles = (Articles) session.load(Articles.class, id);
        }
        catch (Exception e) {
        }
        return articles;
    }
}