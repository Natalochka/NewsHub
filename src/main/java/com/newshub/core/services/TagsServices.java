package com.newshub.core.services;

import com.newshub.core.dao.ArticlesDAO;
import com.newshub.core.dao.ArticlesTagsDAO;
import com.newshub.core.dao.TagsDAO;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import com.newshub.core.domain.Tags;
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

    public TagsServices(Session session) {
        this.session = session;
        tagsDAO = new TagsDAO(session);
        articlesTagsDAO = new ArticlesTagsDAO(session);
        articlesDAO = new ArticlesDAO(session);
    }

    public void addTag(int id, String name) {
        Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        tagsDAO.create(tag);
    }

    public void addTagToArticle(int tagId, int articleId) {
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setArticleId(articlesDAO.get(articleId));
        articlesTagsPK.setTagId(tagsDAO.get(tagId));
        ArticlesTags articlesTags = new ArticlesTags();
        articlesTags.setArticlesTagsPK(articlesTagsPK);
        articlesTagsDAO.create(articlesTags);
    }

    public void editTag(int tagId, String tagName) {
        Tags tag = tagsDAO.get(tagId);
        tag.setName(tagName);
        tagsDAO.update(tag);
    }

    public void deleteTag(int tagId, int articleId) {
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setArticleId(articlesDAO.get(articleId));
        articlesTagsPK.setTagId(tagsDAO.get(tagId));
        articlesTagsDAO.delete(articlesTagsPK);
    }

    public Tags getTag(int id) {
        return tagsDAO.get(id);
    }

    public List<Tags> getAllTags() {
        return tagsDAO.getAll();
    }

    public List<Articles> getArticlesByTagId(int tagId) {
        List<Articles> tempList = new ArrayList();
        for (ArticlesTags articlesTags : articlesTagsDAO.getAll()) {
            if (articlesTags.getArticlesTagsPK().getTagId().getId() == tagId) {
                tempList.add(articlesTags.getArticlesTagsPK().getArticleId());
            }
        }
        return tempList;
    }
}