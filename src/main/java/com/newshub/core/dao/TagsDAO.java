package com.newshub.core.dao;

import com.newshub.core.domain.Tags;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class TagsDAO implements DAO<Integer, Tags> {
    private Session session;

    private Logger logger = Logger.getLogger(TagsDAO.class);

    public TagsDAO(Session session) {
        this.session = session;
    }

    public Integer create(Tags entity) {
        try {
            Integer id = 0;
            session.beginTransaction();
            id = (Integer) session.save(entity);
            session.getTransaction().commit();
            logger.info("Tag created successfully in method create() in class TagsDAO");
            return id;
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class TagsDAO\n" + e);
        }
        return null;
    }

    public void update(Tags entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("Tag updated successfully in method update() in class TagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class TagsDAO\n" + e);
        }
    }

    public void delete(Integer id) {
        try {
            Tags tags = get(id);
            session.delete(tags);
            session.getTransaction().commit();
            logger.info("Tag deleted successfully in method delete() in class TagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class TagsDAO\n" + e);
        }
    }

    public Tags get(Integer id) {
        Tags tags = null;
        try {
            tags = (Tags) session.load(Tags.class, id);
            logger.info("Tag got successfully in method get() in class TagsDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class TagsDAO\n" + e);
        }
        return tags;
    }

    public List<Tags> getAll() {
        return new ArrayList<Tags>() {
            {
                try {
                    addAll(session.createCriteria(Tags.class).list());
                    logger.info("List of all tags got successfully in method getAll() in class TagsDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class TagsDAO\n" + e);
                }
            }
        };
    }
}