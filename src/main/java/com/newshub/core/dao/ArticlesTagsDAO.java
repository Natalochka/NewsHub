package com.newshub.core.dao;

import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/30/2015.
 */
public class ArticlesTagsDAO implements DAO<ArticlesTagsPK, ArticlesTags> {
    private Session session;

    public ArticlesTagsDAO(Session session) {
        this.session = session;
    }

    public void create(ArticlesTags entity) {

        try {

            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void update(ArticlesTags entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }
    }

    public void delete(ArticlesTagsPK id) {
        try{
            ArticlesTags article = get(id);
            session.delete(article);
            session.getTransaction().commit();
        }  catch (Exception e) {

        }
    }

    public ArticlesTags get(ArticlesTagsPK id) {
        ArticlesTags articlesTags = null;
        try{
            articlesTags = (ArticlesTags) session.load(ArticlesTags.class, id);
        }
        catch (Exception e) {

        }
        return articlesTags;
    }

    public List<ArticlesTags> getAll(){
        return new ArrayList<ArticlesTags>(){
            {
                ArticlesTags articles = null;
                try {
                    addAll(session.createCriteria(ArticlesTags.class).list());
                } catch (Exception e){
                }
            }
        };
    }
}
