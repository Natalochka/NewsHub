package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Tags;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie on 17.04.2015.
 */
@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
public class MainController {

    @RequestMapping(method = {RequestMethod.GET})
    public String showMainPage(ModelMap modelMap) {
        //Locale.setDefault(Locale.ENGLISH);
        return "main_page";
    }

    @ModelAttribute("articlesList") // возвращают на страницу аттрибут, из которого можно полкчать значения
    public List<Articles> getArticlesList() {
        List<Articles> listOfArticles = new Access().getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : listOfArticles) {
            if (listOfArticle.getApproved() && !listOfArticle.getArchived()) {
                approvedArticles.add(listOfArticle);
            }
        }
        return approvedArticles;
    }

    @ModelAttribute("tagsList")
    public List<Tags> getTagsList() {
        return new Access().getAllTags();
    }
}

