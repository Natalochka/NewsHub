package com.newshub.core.domain;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Transactional
@Entity
public class ArticlesTags implements Serializable, HibernateEntity {
    private static final long serialVersionUID = 12132121223113L;

    @EmbeddedId
    private ArticlesTagsPK articlesTagsPK;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesTags that = (ArticlesTags) o;

        return !(articlesTagsPK != null ? !articlesTagsPK.equals(that.articlesTagsPK) : that.articlesTagsPK != null);

    }

    @Override
    public int hashCode() {
        return articlesTagsPK != null ? articlesTagsPK.hashCode() : 0;
    }

    public ArticlesTagsPK getArticlesTagsPK() {
        return articlesTagsPK;
    }

    public void setArticlesTagsPK(ArticlesTagsPK articlesTagsPK) {
        this.articlesTagsPK = articlesTagsPK;
    }
}
