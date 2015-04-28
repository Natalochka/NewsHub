package com.newshub.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 4/27/2015.
 */
public class ArticlesTagsPK implements HibernateEntity, Serializable {
    private int articleId;
    private int tagId;

    @Column(name = "article_id")
    @Id
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Column(name = "tag_id")
    @Id
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesTagsPK that = (ArticlesTagsPK) o;

        if (articleId != that.articleId) return false;
        if (tagId != that.tagId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleId;
        result = 31 * result + tagId;
        return result;
    }
}
