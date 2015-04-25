package com.newshub.core.domain;

import javax.persistence.*;

/**
 * Created by Natalie on 25.04.2015.
 */
@Entity
@Table(name = "TAGS_ARTICLES", schema = "NEWSHUB", catalog = "")
@IdClass(TagsArticlesEntityPK.class)
public class TagsArticlesEntity {
    private Integer articleId;
    private Integer tagId;

    @Id
    @Column(name = "ARTICLE_ID")
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Id
    @Column(name = "TAG_ID")
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

        TagsArticlesEntity that = (TagsArticlesEntity) o;

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
