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
public class UsersArticles implements HibernateEntity, Serializable{
    private static final long serialVersionUID = 121321213133L;
    @EmbeddedId
private UsersArticlesPK usersArticlesPK;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersArticles that = (UsersArticles) o;

        return !(usersArticlesPK != null ? !usersArticlesPK.equals(that.usersArticlesPK) : that.usersArticlesPK != null);

    }

    @Override
    public int hashCode() {
        return usersArticlesPK != null ? usersArticlesPK.hashCode() : 0;
    }

    public UsersArticlesPK getUsersArticlesPK() {
        return usersArticlesPK;
    }

    public void setUsersArticlesPK(UsersArticlesPK usersArticlesPK) {
        this.usersArticlesPK = usersArticlesPK;
    }
}
