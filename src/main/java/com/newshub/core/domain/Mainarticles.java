package com.newshub.core.domain;

import javax.persistence.*;

/**
 * Created by Natalie_2 on 4/29/2015.
 */
@Entity
public class MainArticles implements HibernateEntity {
    private int mainArticleId;
    private Boolean featured;
    private Articles articlesByArticleId;

    @Id
    @Column(name = "main_article_id")
    public int getMainArticleId() {
        return mainArticleId;
    }

    public void setMainArticleId(int mainArticleId) {
        this.mainArticleId = mainArticleId;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainArticles that = (MainArticles) o;

        if (mainArticleId != that.mainArticleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mainArticleId;
    }

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    public Articles getArticlesByArticleId() {
        return articlesByArticleId;
    }

    public void setArticlesByArticleId(Articles articlesByArticleId) {
        this.articlesByArticleId = articlesByArticleId;
    }
}
