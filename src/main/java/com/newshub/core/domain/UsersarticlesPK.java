package com.newshub.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Embeddable
public class UsersArticlesPK implements Serializable, HibernateEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne
    @JoinColumn(name="article_id", nullable = false)
    private Articles articleId;

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
