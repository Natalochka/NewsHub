package com.newshub.core.dao;

import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/30/2015.
 */
public class ArticlesTagsDAO implements DAO<ArticlesTagsPK, ArticlesTags> {
    private Session session;

    private Logger logger = Logger.getLogger(ArticlesTagsDAO.class);

    public ArticlesTagsDAO(Session session) {
        this.session = session;
    }

    public void create(ArticlesTags entity) {

        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("ArticlesTags entity created successfully in method create() in class ArticlesTagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class ArticlesTagsDAO\n" + e);
        }
    }

    public void update(ArticlesTags entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("ArticlesTags entity updated successfully in method update() in class ArticlesTagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class ArticlesTagsDAO\n" + e);
        }
    }

    public void delete(ArticlesTagsPK id) {
        try {
            ArticlesTags article = get(id);
            session.delete(article);
            session.getTransaction().commit();
            logger.info("ArticlesTags entity deleted successfully in method delete() in class ArticlesTagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class ArticlesTagsDAO\n" + e);
        }
    }

    public ArticlesTags get(ArticlesTagsPK id) {
        ArticlesTags articlesTags = null;
        try {
            articlesTags = (ArticlesTags) session.load(ArticlesTags.class, id);
            logger.info("ArticlesTags entity got successfully in method get() in class ArticlesTagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class ArticlesTagsDAO\n" + e);
        }
        return articlesTags;
    }

    public List<ArticlesTags> getAll() {
        return new ArrayList<ArticlesTags>() {
            {
                try {
                    addAll(session.createCriteria(ArticlesTags.class).list());
                    logger.info("List of all ArticlesTags entities got successfully in method getAll() in class ArticlesTagsDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class ArticlesTagsDAO\n" + e);
                }
            }
        };
    }
}
