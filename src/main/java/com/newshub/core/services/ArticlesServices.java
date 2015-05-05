package com.newshub.core.services;

import com.newshub.core.dao.ArticlesDAO;
import com.newshub.core.domain.Articles;

/**
 * Created by Natalie on 25.04.2015.
 */
public class ArticlesServices {

    private ArticlesDAO articlesDAO;

    public ArticlesServices() {
        articlesDAO = new ArticlesDAO();
    }

    public void addArticle(int id, String title, String content) {
        Articles article = new Articles();
        article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        articlesDAO.create(article);
    }

    public void editArticle(int id, String title, String content) {
        Articles oldArticle = articlesDAO.get(id);
        Articles article = new Articles();
        article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        article.setApproved(oldArticle.getApproved());
        article.setPublicationDate(oldArticle.getPublicationDate());
        articlesDAO.update(article);
    }

    public void approveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setApproved(true);
        articlesDAO.update(article);
    }

    public void archiveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setArchived(flag);
        articlesDAO.update(article);
    }

    public void featureArticle(int id, Boolean flag) {
        Articles article = new Articles();
        article.setFeatured(flag);
        articlesDAO.update(article);
    }

    public void deleteArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        articlesDAO.delete(id);
    }

    public Articles getArticle(int id){
        return articlesDAO.get(id);
    }
}
