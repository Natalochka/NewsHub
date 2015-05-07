package com.newshub.core.dao;

import com.newshub.core.domain.Users;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class UsersDAO implements DAO<Integer, Users> {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public void create(Users entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void update(Users entity) {
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
            Users users = get(id);
            session.delete(users);
            session.getTransaction().commit();
        }
        catch (Exception e) {

        }

    }

    public Users get(Integer id) {
        Users users = null;
        try{
            users = (Users) session.load(Users.class, id);
        }
        catch (Exception e) {

        }
        return users;
    }

    public List<Users> getAll()   {
        return new ArrayList<Users>(){
            {
                Users articles = null;
                try {
                    addAll(session.createCriteria(Users.class).list());
                } catch (Exception e) {   // ???
                }
            }
        };
    }
}
