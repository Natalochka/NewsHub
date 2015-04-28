package com.newshub.core.dao;

import com.newshub.core.domain.Articles;

/**
 * Created by Natalie_2 on 4/27/2015.
 */

public class ArticlesDAO implements DAO<Articles>{

    public void create (Articles entity) {}

    public void update (int id, Articles entity){}

    public void delete (int id){}

    public Articles get (int id){
        return null;
    }
}