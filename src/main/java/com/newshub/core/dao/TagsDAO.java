package com.newshub.core.dao;

import com.newshub.core.domain.Tags;
import com.newshub.core.utils.HibernateUtils;
import org.hibernate.Session;

import javax.swing.*;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class TagsDAO implements DAO<Integer, Tags>{
    public void create(Tags entity) {
        Session session = null;
        try {
            session = new HibernateUtils().getSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public void update(Tags entity) {
        Session session = null;
        try {
            session = new HibernateUtils().getSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public void delete(Integer id) {
        Session session = null;
        try{
            Tags tags = get(id);
            session = new HibernateUtils().getSession();
            session.delete(tags);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public Tags get(Integer id) {
        Session session = null;
        Tags tags = null;
        try{
            session = new HibernateUtils().getSession();
            tags = (Tags) session.load(Tags.class, id);
        }
        catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return tags;
    }
}
