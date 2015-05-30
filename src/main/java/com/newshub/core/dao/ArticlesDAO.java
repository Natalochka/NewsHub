package com.newshub.core.dao;

import com.newshub.core.domain.Articles;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Integer, Articles> {
    private Session session;

    private Logger logger = Logger.getLogger(ArticlesDAO.class);

    public ArticlesDAO(Session session) {
        this.session = session;
    }

    public void create(Articles entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("Article created successfully in method create() class ArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class ArticlesDAO\n" + e);
        }
    }

    public void update(Articles entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("Article updated successfully in method update() in class ArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class ArticlesDAO\n" + e);
        }
    }

    public void delete(Integer id) {
        try {
            Articles article = get(id);
            session.delete(article);
            session.getTransaction().commit();
            logger.info("Article deleted successfully in method delete() in class ArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class ArticlesDAO\n" + e);
        }
    }

    public Articles get(Integer id) {
        Articles articles = null;
        try {
            articles = (Articles) session.load(Articles.class, id);
            logger.info("Article got successfully in method get() in class ArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class ArticlesDAO\n" + e);
        }
        return articles;
    }

    public List<Articles> getAll() {
        return new ArrayList<Articles>() {
            {
                try {
                    addAll(session.createCriteria(Articles.class).list());
                    logger.info("List of all articles got successfully in method getAll() in class ArticlesDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class ArticlesDAO\n" + e);
                }
            }
        };
    }
}