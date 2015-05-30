package com.newshub.core.services;

import com.newshub.core.dao.ArticlesDAO;
import com.newshub.core.dao.ArticlesTagsDAO;
import com.newshub.core.dao.TagsDAO;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import com.newshub.core.domain.Tags;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie on 25.04.2015.
 */
public class TagsServices {
    private Session session;
    private TagsDAO tagsDAO;
    private ArticlesTagsDAO articlesTagsDAO;
    private ArticlesDAO articlesDAO;

    private Logger logger = Logger.getLogger(TagsServices.class);

    public TagsServices(Session session) {
        this.session = session;
        tagsDAO = new TagsDAO(this.session);
        articlesTagsDAO = new ArticlesTagsDAO(this.session);
        articlesDAO = new ArticlesDAO(this.session);
    }

    public void addTag(int id, String name) {
        Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        tagsDAO.create(tag);
        logger.info("Tag added successfully in method addTag() in class TagsServices");
    }

    public void addTagToArticle(int tagId, int articleId) {
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setArticleId(articlesDAO.get(articleId));
        articlesTagsPK.setTagId(tagsDAO.get(tagId));
        ArticlesTags articlesTags = new ArticlesTags();
        articlesTags.setArticlesTagsPK(articlesTagsPK);
        articlesTagsDAO.create(articlesTags);
        logger.info("Tag added to article successfully in method addTagToArticle() in class TagsServices");
    }

    public void editTag(int tagId, String tagName) {
        Tags tag = tagsDAO.get(tagId);
        tag.setName(tagName);
        tagsDAO.update(tag);
        logger.info("Tag edited successfully in method editTag() in class TagsServices");
    }

    public void deleteTag(int tagId, int articleId) {
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setArticleId(articlesDAO.get(articleId));
        articlesTagsPK.setTagId(tagsDAO.get(tagId));
        articlesTagsDAO.delete(articlesTagsPK);
        logger.info("Tag deleted successfully in method deleteTag() in class TagsServices");
    }

    public Tags getTag(int id) {
        logger.info("Tag got successfully in method getTag() in class TagsServices");
        return tagsDAO.get(id);
    }

    public List<Tags> getAllTags() {
        logger.info("List of all tags got successfully in method getAllTags() in class TagsServices");
        return tagsDAO.getAll();
    }

    public List<Articles> getArticlesByTagId(int tagId) {
        List<Articles> tempList = new ArrayList();
        for (ArticlesTags articlesTags : articlesTagsDAO.getAll()) {
            if (articlesTags.getArticlesTagsPK().getTagId().getId() == tagId) {
                tempList.add(articlesTags.getArticlesTagsPK().getArticleId());
            }
        }
        logger.info("List of articles got successfully in method getArticlesByTagId() in class TagsServices");
        return tempList;
    }

    public List<Tags> getTagsByArticleId (int articleId){
        List<Tags> tempList = new ArrayList();
        for (ArticlesTags articlesTags : articlesTagsDAO.getAll()) {
            if (articlesTags.getArticlesTagsPK().getArticleId().getId() == articleId) {
                tempList.add(articlesTags.getArticlesTagsPK().getTagId());
            }
        }
        logger.info("List of tags got successfully in method getTagsByArticleId() in class TagsServices");
        return tempList;
    }
}