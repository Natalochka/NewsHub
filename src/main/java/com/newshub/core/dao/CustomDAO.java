package com.newshub.core.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Natalie_2 on 5/21/2015.
 */
public class CustomDAO {

    private Session session;

    public CustomDAO (Session session){
        this.session = session;
    }

    public List runCustomQuery(String query, HashMap<String, Type> scalars , HashMap<Integer, Object> parameters){
        SQLQuery sqlQuery = session.createSQLQuery(query); //"Select ID, NAME FROM USERS WHERE ID = ? and name = ?;
        for(Map.Entry<String, Type> scalar : scalars.entrySet()){
            sqlQuery.addScalar(scalar.getKey(), scalar.getValue());
        }
        for(Map.Entry<Integer, Object> parameter : parameters.entrySet()){
            sqlQuery.setParameter(parameter.getKey(), parameter.getValue());
        }
        return sqlQuery.list();
    }

    public <T> T simpleQuery (String query){
        SQLQuery sqlQuery = session.createSQLQuery(query);
        return (T) sqlQuery.list().get(0);
    }
}
