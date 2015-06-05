package com.newshub.core.utils;

import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Tags;
import com.newshub.core.domain.Users;

import java.util.List;

public class ArticlesInfoEntity {
    private Articles article;
    private List<Tags> tags;
    private Users user;

    public ArticlesInfoEntity(Articles article, List<Tags> tags, Users user) {
        this.article = article;
        this.tags = tags;
        this.user = user;
    }

    public ArticlesInfoEntity() {
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}