package com.newshub.core.dao;

import com.newshub.core.domain.HibernateEntity;

import java.util.List;

/**
 * Created by Natalie on 24.04.2015.
 */

public interface DAO<K, T extends HibernateEntity>{

    K create (T entity);
    void update (T entity);
    void delete(K id);
    T get(K id);
    List<T> getAll();
}