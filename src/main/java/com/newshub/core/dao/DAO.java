package com.newshub.core.dao;

import com.newshub.core.domain.HibernateEntity;

/**
 * Created by Natalie on 24.04.2015.
 */

public interface DAO<K, T extends HibernateEntity>{

    void create (T entity);
    void update (T entity);
    void delete(K id);
    T get(K id);
}