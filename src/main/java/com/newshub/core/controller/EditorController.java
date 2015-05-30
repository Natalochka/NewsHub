package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 5/18/2015.
 */

@Controller
@RequestMapping("/editor")
public class EditorController {

    private Logger logger = Logger.getLogger(EditorController.class);

    @ModelAttribute("checkedArticles")
    public List<Articles> getAllCheckedArticles() {
        List<Articles> listOfArticles = new Access().getAllArticles();
        List<Articles> checkedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : listOfArticles) {
            if (listOfArticle.getChecked() && !listOfArticle.getArchived()){
                checkedArticles.add(listOfArticle);
            }
        }
        logger.info("List of all checked articles got successfully in method getAllCheckedArticles() in class EditorController");
        return checkedArticles;
    }

    @ModelAttribute("uncheckedArticles")
    public List<Articles> getAllUncheckedArticles() {
        List<Articles> listOfArticles = new Access().getAllArticles();
        List<Articles> unCheckedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : listOfArticles) {
            if (!listOfArticle.getChecked() && !listOfArticle.getArchived()){
                unCheckedArticles.add(listOfArticle);
            }
        }
        logger.info("List of all unchecked articles got successfully in method getAllUnCheckedArticles() in class EditorController");
        return unCheckedArticles;
    }

    @ModelAttribute("articlesList")
    public List<Articles> getArticlesList() {
        logger.info("List of articles got successfully in method getArticlesList() in class EditorController");
        return new Access().getAllArticles();
    }
}