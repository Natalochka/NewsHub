package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Tags;
import com.newshub.core.domain.Users;
import com.newshub.core.utils.ArticlesInfoEntity;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String showCorrectorPage(ModelMap model, @ModelAttribute("access") Access access, HttpServletRequest request) {
        logger.info("Corrector page shown successfully in method showCorrectorPage() in class CorrectorController");
        request.getSession().setAttribute("access", access);
        return "redirect:/corrector/drafts/page/1"; //?
    }

    @RequestMapping(value = "/redirect_to_page/{page}", method = RequestMethod.GET)
    public String pagesRedirect(@PathVariable String page, ModelMap modelMap) {
        logger.info("Page redirect performed successfully in method pagesRedirect() in class CorrectorController");
        return "redirect:/corrector/" + page + "/page/1"; //?
    }

    public List<ArticlesInfoEntity> getAllBeingProcessedByCorrector(Access access) {
        List<Articles> list = new ArrayList<Articles>();
        for(Articles article: access.getAllArticles()) {
            if (!(article.getChecked()) && !(article.getDraft())) {
                list.add(article);
            }
        }

        List<ArticlesInfoEntity> articlesInfoEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : list) {
            ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
            articlesInfoEntity.setArticle(article);
            List<Tags> tags = access.getTagsByArticleId(article.getId());
            articlesInfoEntity.setTags(tags);
            articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
            articlesInfoEntities.add(articlesInfoEntity);
        }
        logger.info("List of all articles being processed by corrector got successfully in method getAllBeingProcessedByCorrector() in class CorrectorController");
        return articlesInfoEntities;
    }

    public List<ArticlesInfoEntity> getAllHaveBeenProcessedByCorrector(Access access){
        List<Articles> list = new ArrayList<Articles>();
        for(Articles article: access.getAllArticles()) {
            if (article.getChecked()) {
                list.add(article);
            }
        }

        List<ArticlesInfoEntity> articlesInfoEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : list) {
            ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
            articlesInfoEntity.setArticle(article);
            List<Tags> tags = access.getTagsByArticleId(article.getId());
            articlesInfoEntity.setTags(tags);
            articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
            articlesInfoEntities.add(articlesInfoEntity);
        }
        logger.info("List of all articles have been processed by corrector got successfully in method getAllHaveBeenProcessedByCorrector() in class CorrectorController");
        return articlesInfoEntities;


    }

    @RequestMapping(value = "/being_processed_by_corrector/page/{pageNumber}", method = RequestMethod.GET)
    public String showBeingProcessedByCorrector(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllBeingProcessedByCorrector(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if(count == 10 || (i == allEntities.size() - 1)) {
                tempList.add(allEntities.get(i));
                allArticles.add(tempList);
                tempList = new ArrayList<ArticlesInfoEntity>();
                count = 0;
            } else {
                tempList.add(allEntities.get(i));
            }
            ++count;
        }
        List<Integer> listOfPagesNumbers = new ArrayList<Integer>();
        model.addAttribute("articles_groups", allArticles);
        model.addAttribute("articlesInfo", allArticles.get(pageNumber - 1));
        for(int i = 0; i < allArticles.size(); i++) {
            listOfPagesNumbers.add(i + 1);
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "articles");
        model.addAttribute("current_page", "being_processed_by_corrector");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("All articles being processed by corrector shown successfully in method showBeingProcessedByCorrector() in class CorrectorController");
        return "articles_30";
    }

    @RequestMapping(value = "/have_been_processed_by_corrector/page/{pageNumber}", method = RequestMethod.GET)
    public String showHaveBeenProcessedByCorrector(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllHaveBeenProcessedByCorrector(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if(count == 10 || (i == allEntities.size() - 1)) {
                tempList.add(allEntities.get(i));
                allArticles.add(tempList);
                tempList = new ArrayList<ArticlesInfoEntity>();
                count = 0;
            } else {
                tempList.add(allEntities.get(i));
            }
            ++count;
        }
        List<Integer> listOfPagesNumbers = new ArrayList<Integer>();
        model.addAttribute("articles_groups", allArticles);
        model.addAttribute("articlesInfo", allArticles.get(pageNumber - 1));
        for(int i = 0; i < allArticles.size(); i++) {
            listOfPagesNumbers.add(i + 1);
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "articles");
        model.addAttribute("current_page", "being_processed_by_corrector");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("All articles have been processed by corrector shown successfully in method showHaveBeenProcessedByCorrector() in class CorrectorController");
        return "articles_30";
    }

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
