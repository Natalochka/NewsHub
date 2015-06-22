package com.newshub.core.dao;

import com.newshub.core.domain.UsersArticles;
import com.newshub.core.domain.UsersArticlesPK;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
public class UsersArticlesDAO implements DAO<UsersArticlesPK, UsersArticles> {
    private Session session;

    private Logger logger = Logger.getLogger(UsersArticlesDAO.class);

    public UsersArticlesDAO(Session session) {
        this.session = session;
    }

    public UsersArticlesPK create(UsersArticles entity) {
        try {
            session.beginTransaction();
            UsersArticlesPK id = new UsersArticlesPK();
            id = (UsersArticlesPK) session.save(entity);
            session.getTransaction().commit();
            logger.info("UsersArticles entity created successfully in method create() in class UsersArticlesDAO");
            return id;
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class UsersArticlesDAO\n" + e);
        }
        return null;
    }

    public void update(UsersArticles entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("UsersArticles entity updated successfully in method update() in class UsersArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class UsersArticlesDAO\n" + e);
        }
    }

    public void delete(UsersArticlesPK id) {
        try {
            UsersArticles usersArticles = get(id);
            session.delete(usersArticles);
            session.getTransaction().commit();
            logger.info("UsersArticles entity deleted successfully in method delete() in class UsersArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class UsersArticlesDAO\n" + e);
        }
    }

    public UsersArticles get(UsersArticlesPK id) {
        UsersArticles usersArticles = null;
        try {
            usersArticles = (UsersArticles) session.load(UsersArticles.class, id);
            logger.info("UsersArticles entity got successfully in method get() in class UsersArticlesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class UsersArticlesDAO\n" + e);
        }
        return usersArticles;
    }

    public List<UsersArticles> getAll() {
        return new ArrayList<UsersArticles>() {
            {
                try {
                    addAll(session.createCriteria(UsersArticles.class).list());
                    logger.info("List of UsersArticles entities got successfully in method getAll() in class UsersArticlesDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class UsersArticlesDAO\n" + e);
                }
            }
        };
    }
}
