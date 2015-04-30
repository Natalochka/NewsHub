package com.newshub.core.dao;

import com.newshub.core.domain.Articles;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Integer, Articles>{

    public void create (Articles entity) {}

    public void update (Articles entity){
    }

    public void delete (Integer id){
/*Articles article = get(id);
        Session session = null;
        session = new HibernateUtils().getSession();
        session.delete(article);
        session.getTransaction().commit();*/
    }

    public Articles get (Integer id){
        return null;
    }
}