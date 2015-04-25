package com.newshub.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Natalie on 25.04.2015.
 */
public class TagsArticlesEntityPK implements Serializable {
    private Integer articleId;
    private Integer tagId;

    @Column(name = "ARTICLE_ID")
    @Id
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Column(name = "TAG_ID")
    @Id
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagsArticlesEntityPK that = (TagsArticlesEntityPK) o;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleId != null ? articleId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }
}
