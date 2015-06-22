package com.newshub.core.services;

import com.newshub.core.dao.CustomDAO;
import com.newshub.core.utils.HibernateUtils;
import com.newshub.core.utils.QueryRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.hibernate.type.StandardBasicTypes;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Natalie_2 on 5/21/2015.
 */
public class CustomServices {
    private CustomDAO customDAO;
    private Logger logger = Logger.getLogger(CustomServices.class);

    public CustomServices(Session session) {
        this.customDAO = new CustomDAO(session);
    }

    public Integer getMaxNumberOnMain(){
        logger.info("Maximal number on main got successfully in method getMaxNumberOnMain() in class CustomServices");
        return customDAO.<Integer>simpleQuery(QueryRepository.getNumberOnMain);
    }

    public String getLastImageNumber(){
        logger.info("Last image number got successfully in method getLastImageNumber() in class CustomServices");
        return customDAO.<String>simpleQuery(QueryRepository.getLastImageNumber);
    }

    public List<Integer> getSearchedArticles(String title) {
        Session session = new HibernateUtils().getSession();
        CustomDAO customDAO = new CustomDAO(session);
        List<Integer> list = (List<Integer>) customDAO.runCustomQuery(QueryRepository.getArticlesByTitle, new HashMap<String, Type>() {{
            put("id", StandardBasicTypes.INTEGER);
        }}, new HashMap<Integer, Object>() {{put(0, title);}});
        logger.info("Searched rticles got successfully in method getSearchedArticles() in class CustomServices");
        return list;
    }
}
