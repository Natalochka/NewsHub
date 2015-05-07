package com.newshub.core.dao;

import com.newshub.core.domain.Tags;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class TagsDAO implements DAO<Integer, Tags>{

    private Session session;

    public void create(Tags entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }

    }

    public void update(Tags entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }
    }

    public void delete(Integer id) {
        try{
            Tags tags = get(id);
            session.delete(tags);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }
    }

    public Tags get(Integer id) {
        Tags tags = null;
        try{
            tags = (Tags) session.load(Tags.class, id);
        }
        catch (Exception e) {

        }
        return tags;
    }
}
