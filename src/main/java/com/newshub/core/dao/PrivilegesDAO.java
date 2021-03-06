package com.newshub.core.dao;

import com.newshub.core.domain.Privileges;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class PrivilegesDAO implements DAO<Integer, Privileges> {
    private Session session;

    private Logger logger = Logger.getLogger(PrivilegesDAO.class);

    public PrivilegesDAO(Session session) {
        this.session = session;
    }

    public Integer create(Privileges entity) {
        try {
            Integer id = 0;
            session.beginTransaction();
            id = (Integer) session.save(entity);
            session.getTransaction().commit();
            logger.info("Privilege created successfully in method create() in class PrivilegesDAO");
            return id;
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class PrivilegesDAO\n" + e);
        }
        return null;
    }

    public void update(Privileges entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("Privilege updated successfully in method update() in class PrivilegesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class PrivilegesDAO\n" + e);
        }
    }

    public void delete(Integer id) {
        try {
            Privileges privileges = get(id);
            session.delete(privileges);
            session.getTransaction().commit();
            logger.info("Privilege deleted successfully in method delete() in class PrivilegesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class PrivilegesDAO\n" + e);
        }
    }

    public Privileges get(Integer id) {
        Privileges privileges = null;
        try {
            privileges = (Privileges) session.load(Privileges.class, id);
            logger.info("Privilege got successfully in method get() in class PrivilegesDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class PrivilegesDAO\n" + e);
        }
        return privileges;
    }

    public List<Privileges> getAll() {
        return new ArrayList<Privileges>() {
            {
                try {
                    addAll(session.createCriteria(Privileges.class).list());
                    logger.info("List of all privileges got successfully in method getAll() in class PrivilegesDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class PrivilegesDAO\n" + e);
                }
            }
        };
    }
}