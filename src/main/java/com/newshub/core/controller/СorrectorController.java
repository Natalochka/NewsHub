package com.newshub.core.controller;


import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Users;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by admin on 16.05.2015.
 */

@Controller
@RequestMapping(value = "/corrector")
public class —orrectorController {

    //private Articles articles;

    @ModelAttribute("articlesList")
    //@ResponseBody
    public List<Articles> getAllArticles(){
        return  new Access().getAllArticles();
    }

    @RequestMapping(value ="/add" ,method = RequestMethod.POST)
    @ResponseBody
    public void setArticle(HttpServletRequest httpServletRequest){
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        String title = httpServletRequest.getParameter("title");
        String content = httpServletRequest.getParameter("content");
        new Access().addArticle(id, title, content);
    }


    @ModelAttribute("draftsList")
    public List<Articles> getDrafts(){
        List<Articles> allArticles = new Access().getAllArticles();
        List<Articles> drafts = new ArrayList<Articles>();
        for(Articles article: allArticles ){
            if (article.getDraft() == true)
                drafts.add(article);

        }
        return drafts;
    }

    @ModelAttribute("users")
    public Users getSetting(HttpServletRequest request){
        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        Access access = (Access)context.getBean("accessBean");
        return access.getUser(Integer.parseInt(request.getParameter("id")));
    }

    @RequestMapping(value ="/set" ,method = RequestMethod.POST)
    @ResponseBody
    public void setSetting(HttpServletRequest request){
        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        Access access = (Access)context.getBean("accessBean");
        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        access.editUserInfo(id, login, pass, email, firstname, lastname);
    }


}
