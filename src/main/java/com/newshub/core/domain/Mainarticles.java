package com.newshub.core.domain;

import javax.persistence.*;

/**
 * Created by Natalie_2 on 4/27/2015.
 */
@Entity
public class MainArticles implements HibernateEntity {
    private int mainArticleId;
    private Byte featured;
    private Articles articlesByArticleId;

    @Id
    @Column(name = "main_article_id")
    public int getMainArticleId() {
        return mainArticleId;
    }

    public void setMainArticleId(int mainArticleId) {
        this.mainArticleId = mainArticleId;
    }

    @Basic
    @Column(name = "featured")
    public Byte getFeatured() {
        return featured;
    }

    public void setFeatured(Byte featured) {
        this.featured = featured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainArticles that = (MainArticles) o;

        if (mainArticleId != that.mainArticleId) return false;
        if (featured != null ? !featured.equals(that.featured) : that.featured != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mainArticleId;
        result = 31 * result + (featured != null ? featured.hashCode() : 0);
        return result;
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
