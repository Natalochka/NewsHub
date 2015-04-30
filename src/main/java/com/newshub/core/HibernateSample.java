package com.newshub.core;

import com.newshub.core.domain.Articles;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Natalie_2 on 4/30/2015.
 */
public class HibernateSample {
    public static void main(String[] args) {
        HibernateUtils hibernateUtils = new HibernateUtils();
        Session session = hibernateUtils.getSession();
        Articles articles = new Articles();
        Transaction tx = session.beginTransaction();
        articles.setId(12);
        articles.setTitle("asdf");
        articles.setContent("asdf");
        articles.setPublished(true);
        session.save(articles);
        tx.commit();

        tx = session.beginTransaction();
        Articles articles2 = new Articles();
        articles2.setId(12);
        articles2.setTitle("1111");
        articles2.setContent("1111");
        articles2.setPublished(true);
        session.update(articles2);
        tx.commit();
        session.close();
    }
}
