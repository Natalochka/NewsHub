package com.newshub.core.dao;

import com.newshub.core.domain.Tags;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class TagsDAO implements DAO<Integer, Tags> {
    private Session session;

    public TagsDAO(Session session) {
        this.session = session;
    }

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
        } catch (Exception e) {

        }
    }

    public void delete(Integer id) {
        try {
            Tags tags = get(id);
            session.delete(tags);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public Tags get(Integer id) {
        Tags tags = null;
        try {
            tags = (Tags) session.load(Tags.class, id);
        } catch (Exception e) {

        }
        return tags;
    }

    public List<Tags> getAll() {
        return new ArrayList<Tags>() {
            {
                Tags articles = null;
                try {
                    addAll(session.createCriteria(Tags.class).list());
                } catch (Exception e) {
                }
            }
        };
    }
}