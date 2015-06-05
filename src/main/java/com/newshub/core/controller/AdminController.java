

package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Tags;
import com.newshub.core.utils.ArticlesInfoEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie_2 on 6/1/2015.
 */
@Controller
@SessionAttributes("accessAtribute")
@RequestMapping(value = "/admin")
public class AdminController {
    private Logger logger = Logger.getLogger(AdminController.class);


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdminPage(ModelMap model, @ModelAttribute("access") Access access,/* RedirectAttributes redirectAttributes, */HttpServletRequest request) {
        logger.info("Admin page shown successfully in method showAdminPage() in class AdminController");
        request.getSession().setAttribute("access", access);
        return "redirect:/admin/drafts/page/1";
    }
     
       @RequestMapping(value = "/redirect_to_page/{page}", method = RequestMethod.GET)
    public String pagesRedirect(@PathVariable String page, Model model/*, @ModelAttribute("access") Access access,*/ /* RedirectAttributes redirectAttributes,*/ /*HttpServletRequest request*/) {
        logger.info("Page redirect performed successfully in method pagesRedirect() in class MainController");
           return "redirect:/admin/" + page + "/page/1";
    }

    public List<ArticlesInfoEntity> getAllArticlesInfoEntity() {
        Access access = new Access();
        List<Articles> list = access.getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : list) {
            if (listOfArticle.getApproved() && !listOfArticle.getArchived()) {
                approvedArticles.add(listOfArticle);
            }
        }
        for (int i = approvedArticles.size() - 1; i > 0; i--) {
            for(int j = 0; j < i; j++) {
                if(approvedArticles.get(j).getNumberOnMain() > approvedArticles.get(j + 1).getNumberOnMain()) {
                    Articles temp = approvedArticles.get( j );
                    approvedArticles.set(j, approvedArticles.get(j + 1));
                    approvedArticles.set(j + 1, temp);
                }
            }
        }
        List<ArticlesInfoEntity> articlesInfoEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : approvedArticles) {
            ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
            articlesInfoEntity.setArticle(article);
            List<Tags> tags = access.getTagsByArticleId(article.getId());
            articlesInfoEntity.setTags(tags);
            articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
            articlesInfoEntities.add(articlesInfoEntity);
        }
        logger.info("List of ArticlesInfoEntities got successfully in method getAllArticlesInfoEntity() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/main_page/page/{pageNumber}", method = RequestMethod.GET)
    public String pages(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllArticlesInfoEntity();
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
        model.addAttribute("current_nav", "main_page");
        model.addAttribute("current_page", "main_page");
        model.addAttribute("articlesInfo", allArticles.get(pageNumber - 1));
        for(int i = 0; i < allArticles.size(); i++) {
            listOfPagesNumbers.add(i + 1);
        }
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllDrafts(Access access) {
        List<Articles> list = new ArrayList<>();
        for(Articles article: access.getAllArticles()) {
            if (article.getDraft()) {
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
        logger.info("List of all drafts got successfully in method getAllDrafts() in class AdminController");
        return articlesInfoEntities;
    }


    @RequestMapping(value = "/drafts/page/{pageNumber}", method = RequestMethod.GET)
    public String showDrafts(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllDrafts(access);
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
        model.addAttribute("current_page", "drafts");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("Drafts shown successfully in method showDrafts() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllBeingProcessedByCorrector(Access access) {
        List<Articles> list = new ArrayList<>();
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
        logger.info("List of all articles being processed by corrector got successfully in method getAllBeingProcessedByCorrector() in class AdminController");
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
        logger.info("All articles being processed by corrector shown successfully in method showBeingProcessedByCorrector() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllBeingProcessedByEditor(Access access) {
        List<Articles> list = new ArrayList<>();
        for(Articles article: access.getAllArticles()) {
            if ((article.getChecked()) && !(article.getApproved())) {
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
        logger.info("List of all articles being processed by editor got successfully in method getAllBeingProcessedByEditor() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/being_processed_by_editor/page/{pageNumber}", method = RequestMethod.GET)
    public String showBeingProcessedByEditor(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllBeingProcessedByEditor(access);
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
        model.addAttribute("current_page", "being_processed_by_editor");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("All articles being processed by editor shown successfully in method showBeingProcessedByEditor() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllChecked(Access access) { //
        List<Articles> list = new ArrayList<>();
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
        logger.info("List of all checked articles got successfully in method getAllChecked() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/checked/page/{pageNumber}", method = RequestMethod.GET)
    public String showChecked(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access,*/ HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllChecked(access);
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
        model.addAttribute("current_page", "checked");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("Checked articles shown successfully in method showChecked() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getRejected(Access access) { //
        List<Articles> list = new ArrayList<>();
        for(Articles article: access.getAllArticles()) {
            if (article.getRejected()) {
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
        logger.info("List of rejected articles got successfully in method getRejected() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/rejected/page/{pageNumber}", method = RequestMethod.GET)
    public String showRejected(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getRejected(access);
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
        model.addAttribute("current_page", "rejected");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());

        model.addAttribute("access", access);  // change
            /*redirectAttributes.addFlashAttribute("access", access);*/
        logger.info("Rejected articles shown successfully in method showRejected() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllPublished(Access access) { //
        List<Articles> list = new ArrayList<>();
        for(Articles article: access.getAllArticles()) {
            if (article.getApproved()) {
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
        logger.info("List of published articles got successfully in method getAllPublished() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/published/page/{pageNumber}", method = RequestMethod.GET)
    public String showPublished(@PathVariable Integer pageNumber, Model model, /*@ModelAttribute("access") Access access, */HttpServletRequest httpServletRequest/*, RedirectAttributes redirectAttributes*/) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllPublished(access);
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
        model.addAttribute("current_page", "published");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());

        model.addAttribute("access", access);  // change
        logger.info("All published articles shown successfully in method showPublished() in class AdminController");
        return "articles_30";
    }

}

