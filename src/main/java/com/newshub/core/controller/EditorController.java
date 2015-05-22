package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
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

    @ModelAttribute("checkedArticles")
    public List<Articles> getAllCheckedArticles() {
        List<Articles> listOfArticles = new Access().getAllArticles();
        List<Articles> checkedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : listOfArticles) {
            if (listOfArticle.getChecked() && !listOfArticle.getArchived()){
                checkedArticles.add(listOfArticle);
            }
        }
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
        return unCheckedArticles;
    }

    @ModelAttribute("articlesList")
    public List<Articles> getArticlesList() {
        return new Access().getAllArticles();
    }
}