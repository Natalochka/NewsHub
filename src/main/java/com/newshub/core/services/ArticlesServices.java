package com.newshub.core.services;

import com.newshub.core.dao.ArticlesDAO;
import com.newshub.core.domain.Articles;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Natalie on 25.04.2015.
 */
public class ArticlesServices {
    private Session session;
    private ArticlesDAO articlesDAO;

    public ArticlesServices(Session session) {
        this.session = session;
        articlesDAO = new ArticlesDAO(this.session);
    }

    public void addArticle(int id, String title, String content) {
        Articles article = new Articles();
        article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        article.setPublicationDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
        articlesDAO.create(article);
    }

    public void editArticle(int id, String title, String content) {
        Articles article = articlesDAO.get(id);
        article.setTitle(title);
        article.setContent(content);
        articlesDAO.update(article);
    }

    public void checkArticle(int id, Boolean flag) {
        Articles articleForCheck = articlesDAO.get(id);
        articleForCheck.setChecked(flag);
        articlesDAO.update(articleForCheck);
    }

    public void approveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setApproved(flag);
        articlesDAO.update(article);
    }

    public void archiveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setArchived(flag);
        articlesDAO.update(article);
    }

    public void featureArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setFeatured(flag);
        articlesDAO.update(article);
    }

    public void deleteArticle(int id) {
        articlesDAO.delete(id);
    }

    public Articles getArticle(int id) {
        return articlesDAO.get(id);
    }

    public List<Articles> getAllArticles() {
        return articlesDAO.getAll();
    }
}
