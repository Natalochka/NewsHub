package com.newshub.core.services;

import com.newshub.core.dao.ArticleTagsDAO;
import com.newshub.core.dao.TagsDAO;
import com.newshub.core.domain.ArticlesTags;
import com.newshub.core.domain.ArticlesTagsPK;
import com.newshub.core.domain.Tags;

/**
 * Created by Natalie on 25.04.2015.
 */
public class TagsServices {
    TagsDAO tagsDAO = new TagsDAO();
    ArticleTagsDAO articleTagsDAO = new ArticleTagsDAO();

    public void addTag(Integer id, String name){
Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        tagsDAO.create(tag);
    }

    public void addTagToArticle(Integer tegId, Integer articleId) {
        ArticlesTags articlesTags = new ArticlesTags();
        articlesTags.setArticleId(articleId);
        articlesTags.setTagId(tegId);
        articleTagsDAO.create(articlesTags);
    }

    public void editTag(Integer tagId, Integer articleId, String tagName){
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setTagId(tagId);
        articlesTagsPK.setArticleId(articleId);
        Tags tags = new Tags();
        tags.setId(tagId);
        tags.setName(tagName);
        tagsDAO.update(tags);
    }

    public void deleteTag(Integer tagId, Integer articleId){
        ArticlesTagsPK articlesTagsPK = new ArticlesTagsPK();
        articlesTagsPK.setTagId(tagId);
        articlesTagsPK.setArticleId(articleId);
        articleTagsDAO.delete(articlesTagsPK);
    }

    public Tags getTag(int id){
        Tags tags = tagsDAO.get(id);
        return tags;
    }

}