package com.newshub.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Embeddable
public class ArticlesTagsPK implements Serializable, HibernateEntity {
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Articles articleId;

    @ManyToOne
    @JoinColumn(name="tag_id", nullable = false)
    private Tags tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesTagsPK that = (ArticlesTagsPK) o;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        return !(tagId != null ? !tagId.equals(that.tagId) : that.tagId != null);

    }

    @Override
    public int hashCode() {
        int result = articleId != null ? articleId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    public Articles getArticleId() {
        return articleId;
    }

    public void setArticleId(Articles articleId) {
        this.articleId = articleId;
    }

    public Tags getTagId() {
        return tagId;
    }

    public void setTagId(Tags tagId) {
        this.tagId = tagId;
    }
}
