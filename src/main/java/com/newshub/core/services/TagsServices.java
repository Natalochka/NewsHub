package com.newshub.core.services;

import com.newshub.core.dao.ArticlesTagsDAO;
import com.newshub.core.dao.TagsDAO;
import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import com.newshub.core.domain.Tags;
import com.newshub.core.utils.HibernateUtils;

/**
 * Created by Natalie on 25.04.2015.
 */
public class TagsServices {
    TagsDAO tagsDAO = new TagsDAO(new HibernateUtils().getSession());
    ArticlesTagsDAO articleTagsDAO = new ArticlesTagsDAO(new HibernateUtils().getSession());

    public void addTag(Integer id, String name){
Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        tagsDAO.create(tag);
    }

    public void addTagToArticle(Integer tagId, Integer articleId) {
        ArticlesTags articlesTags = new ArticlesTags();
        articleTagsDAO.create(articlesTags);
    }

    public void editTag(Integer tagId, Integer articleId, String tagName){
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        Tags tags = new Tags();
        tags.setId(tagId);
        tags.setName(tagName);
        tagsDAO.update(tags);
    }

    public void deleteTag(Integer tagId, Integer articleId){
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articleTagsDAO.delete(articlesTagsPK);
    }

    public Tags getTag(int id){
        Tags tags = tagsDAO.get(id);
        return tags;
    }

}