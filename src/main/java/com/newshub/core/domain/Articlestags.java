package com.newshub.core.domain;

import javax.persistence.*;

/**
 * Created by Natalie_2 on 4/29/2015.
 */
@Entity
@IdClass(ArticlesTagsPK.class)
public class ArticlesTags implements HibernateEntity {
    private int articleId;
    private int tagId;
    private Articles articlesByArticleId;

    @Id
    @Column(name = "article_id")
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Id
    @Column(name = "tag_id")
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

        ArticlesTags that = (ArticlesTags) o;

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

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Articles getArticlesByArticleId() {
        return articlesByArticleId;
    }

    public void setArticlesByArticleId(Articles articlesByArticleId) {
        this.articlesByArticleId = articlesByArticleId;
    }
}
