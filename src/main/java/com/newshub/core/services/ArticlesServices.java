package com.newshub.core.services;

import com.newshub.core.dao.ArticlesDAO;
import com.newshub.core.dao.ArticlesTagsDAO;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.ArticlesTags;
import org.apache.log4j.Logger;
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
    private ArticlesTagsDAO articlesTagsDAO;

    private Logger logger = Logger.getLogger(ArticlesServices.class);

    public ArticlesServices(Session session) {
        this.session = session;
        articlesDAO = new ArticlesDAO(this.session);
        articlesTagsDAO = new ArticlesTagsDAO(this.session);
    }

    public void addArticle(int id, String title, String content) {
        Articles article = new Articles();
        article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        article.setPublicationDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
        article.setDraft(true);
        articlesDAO.create(article);
        logger.info("Article added successfully in method addArticle() in class ArticlesServices");
    }

    public void editArticle(int id, String title, String content) {
        Articles article = articlesDAO.get(id);
        article.setTitle(title);
        article.setContent(content);
        articlesDAO.update(article);
        logger.info("Article edited successfully in method editArticle() in class ArticlesServices");
    }

    public void checkArticle(int id, Boolean flag) {
        Articles articleForCheck = articlesDAO.get(id);
        articleForCheck.setChecked(flag);
        articlesDAO.update(articleForCheck);
        logger.info("Article checked successfully in method checkArticle() in class ArticlesServices");
    }

    public void approveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setApproved(flag);
        articlesDAO.update(article);
        logger.info("Article approved successfully in method approveArticle() in class ArticlesServices");
    }

    public void archiveArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setArchived(flag);
        articlesDAO.update(article);
        logger.info("Article archived successfully in method archiveArticle() in class ArticlesServices");
    }

    public void featureArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setFeatured(flag);
        articlesDAO.update(article);
        logger.info("Article featured successfully in method featureArticle() in class ArticlesServices");
    }

    public void draftArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setDraft(flag);
        articlesDAO.update(article);
        logger.info("Article drafted successfully in method draftArticle() in class ArticlesServices");
    }

    public void rejectArticle(int id, Boolean flag) {
        Articles article = articlesDAO.get(id);
        article.setReject(flag);
        articlesDAO.update(article);
        logger.info("Article rejected successfully in method rejectArticle() in class ArticlesServices");
    }

    public void addImageToArticle(int id, String imageName) {
        Articles article = articlesDAO.get(id);
        article.setImage(imageName);
        articlesDAO.update(article);
        logger.info("Image added to article successfully in method addImageToArticle() in class ArticlesServices");
    }

    public List<ArticlesTags> getArticlesTags (){
        logger.info("List of ArticlesTags entities got successfully in method getArticlesTags() in class ArticlesServices");
        return articlesTagsDAO.getAll();
    }

    public void deleteArticle(int id) {
        articlesDAO.delete(id);
        logger.info("Article deleted successfully in method deleteArticle() in class ArticlesServices");
    }

    public Articles getArticle(int id) {
        logger.info("Article got successfully in method getArticle() in class ArticlesServices");
        return articlesDAO.get(id);
    }

    public List<Articles> getAllArticles() {
        logger.info("List of all articles got successfully in method getAllArticles() in class ArticlesServices");
        return articlesDAO.getAll();
    }

    public String getImage(int id) {
        logger.info("Image got successfully in method getImage() in class ArticlesServices");
        return articlesDAO.get(id).getImage();
    }
}
