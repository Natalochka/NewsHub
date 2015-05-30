package com.newshub.core.dao;

import com.newshub.core.domain.Users;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class UsersDAO implements DAO<Integer, Users> {
    private Session session;

    private Logger logger = Logger.getLogger(UsersDAO.class);

    public UsersDAO(Session session) {
        this.session = session;
    }

    public void create(Users entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("User created successfully in method create() in class UsersDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method create() in class UsersDAO\n" + e);
        }
    }

    public void update(Users entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("User updated successfully in method update() in class UsersDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method update() in class UsersDAO\n" + e);
        }
    }

    public void delete(Integer id) {
        try{
            Users users = get(id);
            session.delete(users);
            session.getTransaction().commit();
            logger.info("User deleted successfully in method delete() in class UsersDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method delete() in class UsersDAO\n" + e);
        }

    }

    public Users get(Integer id) {
        Users users = null;
        try{
            users = (Users) session.load(Users.class, id);
            logger.info("User got successfully in method get() in class UsersDAO");
        } catch (Exception e) {
            logger.error("Error occurred in method get() in class UsersDAO\n" + e);
        }
        return users;
    }

    public List<Users> getAll()   {
        return new ArrayList<Users>(){
            {
                try {
                    addAll(session.createCriteria(Users.class).list());
                    logger.info("List of all users got successfully in method getAll() in class UsersDAO");
                } catch (Exception e) {
                    logger.error("Error occurred in method getAll() in class UsersDAO\n" + e);
                }
            }
        };
    }
}
