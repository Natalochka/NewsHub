package com.newshub.core.dao;

import com.newshub.core.domain.Privileges;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 4/28/2015.
 */
public class PrivilegesDAO implements DAO<Integer, Privileges> {
    private Session session;

    public PrivilegesDAO(Session session) {
        this.session = session;
    }

    public void create(Privileges entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void update(Privileges entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

        }

    }

    public void delete(Integer id) {
        try {
            Privileges privileges = get(id);
            session.delete(privileges);
            session.getTransaction().commit();
        } catch (Exception e) {

        }

    }

    public Privileges get(Integer id) {
        Privileges privileges = null;
        try {
            privileges = (Privileges) session.load(Privileges.class, id);
        } catch (Exception e) {

        }
        return privileges;
    }

    public List<Privileges> getAll() {
        return new ArrayList<Privileges>() {
            {
                Privileges articles = null;
                try {
                    addAll(session.createCriteria(Privileges.class).list());
                } catch (Exception e) {
                }
            }
        };
    }
}