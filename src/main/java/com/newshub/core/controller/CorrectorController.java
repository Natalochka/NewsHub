package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Users;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16.05.2015.
 */

@Controller
@RequestMapping(value = "/corrector")
public class CorrectorController {

    private Logger logger = Logger.getLogger(CorrectorController.class);

    @RequestMapping(value ="/add", method = RequestMethod.POST)
    @ResponseBody
    public void setArticle(HttpServletRequest httpServletRequest){
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        String title = httpServletRequest.getParameter("title");
        String content = httpServletRequest.getParameter("content");
        new Access().addArticle(id, title, content);
        logger.info("Article set successfully in method setArticle() in class CorrectorController");
    }

    @ModelAttribute("checkedArticlesList")
    public List<Articles> getCheckedArticles(){
        List<Articles> allArticles = new Access().getAllArticles();
        List<Articles> checkedArticles = new ArrayList<Articles>();
        for(Articles article: allArticles ){
            if (article.getChecked() == true)
                checkedArticles.add(article);

        }
        logger.info("List of checked articles got successfully in method getCheckedArticles() in class CorrectorController");
        return checkedArticles;
    }

    @ModelAttribute("uncheckedArticlesList")
    public List<Articles> getUncheckedArticles(){
        List<Articles> allArticles = new Access().getAllArticles();
        List<Articles> uncheckedArticles = new ArrayList<Articles>();
        for(Articles article: allArticles ){
            if (article.getChecked() == false)
                uncheckedArticles.add(article);

        }
        logger.info("List of unchecked articles got successfully in method getUncheckedArticles() in class CorrectorController");
        return uncheckedArticles;
    }

    @ModelAttribute("draftsList")
    public List<Articles> getDrafts(){
        List<Articles> allArticles = new Access().getAllArticles();
        List<Articles> drafts = new ArrayList<Articles>();
        for(Articles article: allArticles ){
            if (article.getDraft() == true)
                drafts.add(article);
        }
        logger.info("List of drafts got successfully in method getDrafts() in class CorrectorController");
        return drafts;
    }

    @ModelAttribute("users")
    public Users getSetting(HttpServletRequest request){
        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        Access access = (Access)context.getBean("accessBean");
        logger.info("Settings got successfully in method getSettings() in class CorrectorController");
        return access.getUser(Integer.parseInt(request.getParameter("id")));
    }

    @RequestMapping(value ="/set", method = RequestMethod.POST)
    @ResponseBody
    public void setSetting(HttpServletRequest request){
        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        Access access = (Access)context.getBean("accessBean");
        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        access.editUserInfo(id, login, pass, email, firstName, lastName);
        logger.info("Settings set successfully in method setSettings() in class CorrectorController");
    }
}
