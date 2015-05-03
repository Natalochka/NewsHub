package com.newshub.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
public class UsersArticlesPK implements Serializable, HibernateEntity {
    private int userId;
    private int articleId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "article_id")
    @Id
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersArticlesPK that = (UsersArticlesPK) o;

        if (userId != that.userId) return false;
        if (articleId != that.articleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + articleId;
        return result;
    }
}
