package com.newshub.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Embeddable
public class UsersArticlesPK implements Serializable, HibernateEntity {
    private static final long serialVersionUID = 121321213L;
    @ManyToOne(cascade=CascadeType.ALL, targetEntity=Users.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne(cascade= CascadeType.ALL, targetEntity=Articles.class, fetch = FetchType.LAZY)
    @JoinColumn(name="article_id", nullable = false)
    private Articles articleId;

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Articles getArticleId() {
        return articleId;
    }

    public void setArticleId(Articles articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersArticlesPK that = (UsersArticlesPK) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return !(articleId != null ? !articleId.equals(that.articleId) : that.articleId != null);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        return result;
    }
}
