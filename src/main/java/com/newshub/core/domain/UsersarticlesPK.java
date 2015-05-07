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
}
