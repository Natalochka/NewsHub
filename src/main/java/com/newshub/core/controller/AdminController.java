package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Privileges;
import com.newshub.core.domain.Tags;
import com.newshub.core.domain.Users;
import com.newshub.core.utils.ArticlesInfoEntity;
import com.newshub.core.utils.MessagesClass;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    public String showAdminPage(ModelMap model, @ModelAttribute("access") Access access, HttpServletRequest request) {
        if(request.getSession().getAttribute("access") == null) request.getSession().setAttribute("access", access);
        logger.info("Admin page shown successfully in method showAdminPage() in class AdminController");
        return "redirect:/admin/drafts/page/1";
    }

    @RequestMapping(value = "/redirect_to_page/{page}", method = RequestMethod.GET)
    public String pagesRedirect(@PathVariable String page, ModelMap modelMap) {
        logger.info("Page redirect performed successfully in method pagesRedirect() in class AdminController");
        return "redirect:/admin/" + page + "/page/1";
    }

    @RequestMapping(value = "/show_tags", method = {RequestMethod.GET})
    public String showAllTags(ModelMap model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);
        model.addAttribute("current_nav", "tags");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("tagsList", access.getAllTags());
        logger.info("All tags shown successfully in method showAllTags() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/tag/page/{id}", method = RequestMethod.GET)
    public String showArticlesByTag(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> articlesInfoEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : access.getArticlesByTagId(id)) {
            ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
            articlesInfoEntity.setArticle(article);
            List<Tags> tags = access.getTagsByArticleId(article.getId());
            articlesInfoEntity.setTags(tags);
            articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
            articlesInfoEntities.add(articlesInfoEntity);
        }
        modelMap.addAttribute("articlesInfo", articlesInfoEntities);
        modelMap.addAttribute("current_nav", "articles_by_tag");
        modelMap.addAttribute("current_page", "articles_by_tag");
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("access", access);  // change
        logger.info("Articles by tag shown successfully in method showArticlesByTag() in class AdminController");
        return "articles_30";
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
            for (int j = 0; j < i; j++) {
                if (approvedArticles.get(j).getNumberOnMain() > approvedArticles.get(j + 1).getNumberOnMain()) {
                    Articles temp = approvedArticles.get(j);
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
        logger.info("List of all ArticlesInfoEntities got successfully in method getAllArticlesInfoEntity() in class AdminController");
        return articlesInfoEntities;
    }

    public List<ArticlesInfoEntity> getAllArticlesInfoEntityRevertSort() {
        Access access = new Access();
        List<Articles> list = access.getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : list) {
            if (listOfArticle.getApproved() && !listOfArticle.getArchived()) {
                approvedArticles.add(listOfArticle);
            }
        }
        for (int i = approvedArticles.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (approvedArticles.get(j).getNumberOnMain() < approvedArticles.get(j + 1).getNumberOnMain()) {
                    Articles temp = approvedArticles.get(j);
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
        logger.info("List of all ArticlesInfoEntities got successfully in method getAllArticlesInfoEntity() in class AdminController");
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
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
        }
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        logger.info("Pagination performed successfully in method pages() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllDrafts(Access access) {
        List<Articles> list = new ArrayList<>();
        for (Articles article : access.getAllArticles()) {
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
    public String showDrafts(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllDrafts(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
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
        for (Articles article : access.getAllArticles()) {
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
    public String showBeingProcessedByCorrector(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllBeingProcessedByCorrector(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
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
        for (Articles article : access.getAllArticles()) {
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
    public String showBeingProcessedByEditor(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllBeingProcessedByEditor(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
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
        for (Articles article : access.getAllArticles()) {
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
    public String showChecked(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllChecked(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
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
        for (Articles article : access.getAllArticles()) {
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
    public String showRejected(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getRejected(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "articles");
        model.addAttribute("current_page", "rejected");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());

        model.addAttribute("access", access);  // change
            /*redirectAttributes.addFlashAttribute("access", access);*/
        logger.info("List of rejected articles shown successfully in method showRejected() in class AdminController");
        return "articles_30";
    }

    public List<ArticlesInfoEntity> getAllPublished(Access access) { //
        List<Articles> list = new ArrayList<>();
        for (Articles article : access.getAllArticles()) {
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
        logger.info("List of all published articles got successfully in method getAllPublished() in class AdminController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/published/page/{pageNumber}", method = RequestMethod.GET)
    public String showPublished(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<ArticlesInfoEntity> allEntities = getAllPublished(access);
        List<List<ArticlesInfoEntity>> allArticles = new ArrayList<List<ArticlesInfoEntity>>();
        int count = 1;
        List<ArticlesInfoEntity> tempList = new ArrayList<ArticlesInfoEntity>();
        for (int i = 0; i < allEntities.size(); i++) {
            if (count == 10 || (i == allEntities.size() - 1)) {
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "articles");
        model.addAttribute("current_page", "published");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());

        model.addAttribute("access", access);  // change
        logger.info("List of all published articles shown successfully in method showPublished() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_users/page/{pageNumber}", method = RequestMethod.GET)
    public String showUsers(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<Users> allUsers = access.getAllUsers();
        List<Users> allUsersWithoutAdmin = new ArrayList<>();
        for (Users allUser : allUsers) {
            if(!allUser.getPrivilegesByPrivilegeId().getName().equals("admin")) {
                allUsersWithoutAdmin.add(allUser);
            }
        }
        List<List<Users>> allUsersList = new ArrayList<List<Users>>();
        int count = 1;
        List<Users> tempList = new ArrayList<Users>();
        for (int i = 0; i < allUsersWithoutAdmin.size(); i++) {
            if (count == 10 || (i == allUsersWithoutAdmin.size() - 1)) {
                tempList.add(allUsersWithoutAdmin.get(i));
                allUsersList.add(tempList);
                tempList = new ArrayList<Users>();
                count = 0;
            } else {
                tempList.add(allUsersWithoutAdmin.get(i));
            }
            ++count;
        }
        List<Integer> listOfPagesNumbers = new ArrayList<Integer>();
        model.addAttribute("users_groups", allUsersList);
        model.addAttribute("usersInfo", (allUsersList.size() > 0) ? allUsersList.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if (allUsersList.size() > 0) {
            for (int i = 0; i < allUsersList.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "show_users");
        model.addAttribute("current_page", "show_users");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("Users shown successfully in method showUsers() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_privileges/page/{pageNumber}", method = RequestMethod.GET)
    public String showPrivileges(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<Privileges> allPrivileges = access.getAllPrivileges();
        List<Privileges> allPrivilegesWithoutAdmin = new ArrayList<>();
        for (Privileges allPrivilege : allPrivileges) {
            if(!allPrivilege.getName().equals("admin")) {
                allPrivilegesWithoutAdmin.add(allPrivilege);
            }
        }
        List<List<Privileges>> allPrivilegesList = new ArrayList<List<Privileges>>();
        int count = 1;
        List<Privileges> tempList = new ArrayList<Privileges>();
        for (int i = 0; i < allPrivilegesWithoutAdmin.size(); i++) {
            if (count == 10 || (i == allPrivilegesWithoutAdmin.size() - 1)) {
                tempList.add(allPrivilegesWithoutAdmin.get(i));
                allPrivilegesList.add(tempList);
                tempList = new ArrayList<Privileges>();
                count = 0;
            } else {
                tempList.add(allPrivilegesWithoutAdmin.get(i));
            }
            ++count;
        }
        List<Integer> listOfPagesNumbers = new ArrayList<Integer>();
        model.addAttribute("privileges_groups", allPrivilegesList);
        model.addAttribute("privilegesInfo", (allPrivilegesList.size() > 0) ? allPrivilegesList.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        for (int i = 0; i < allPrivilegesList.size(); i++) {
            listOfPagesNumbers.add(i + 1);
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "show_privileges");
        model.addAttribute("current_page", "show_privileges");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("Privileges shown successfully in method showPrivileges() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_new_article", method = RequestMethod.GET)
    public String showNewArticle(ModelMap model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);  // change
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("current_nav", "new_article");
        model.addAttribute("current_page", "new_article");
        model.addAttribute("tags_list", access.getAllTags());
        logger.info("New article page shown successfully in method showNewArticle() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_user_create", method = RequestMethod.POST)
    public String showNewUserCreation(ModelMap model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);  // change
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("current_nav", "create_user");
        model.addAttribute("current_page", "create_user");
        logger.info("New article page shown successfully in method showNewArticle() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/save_tag/{id}")
    public String saveChosenTag(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);  // change
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("current_nav", "new_article");
        List<Tags> tagsList = (List<Tags>) httpServletRequest.getSession().getAttribute("new_article_tags");
        if (tagsList == null) {
            tagsList = new ArrayList<Tags>();
            tagsList.add(access.getTag(id));
        } else {
            tagsList.add(access.getTag(id));
        }
        httpServletRequest.getSession().setAttribute("new_article_tags", tagsList);
        logger.info("Chosen tag saved successfully in method saveChosenTag() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/save_article", method = RequestMethod.POST) //*
    public String saveNewArticle(ModelMap modelMap, MultipartHttpServletRequest httpServletRequest) {
        String title = "";
        String content = "";
        try {
            title= new String(httpServletRequest.getParameter("title_text").getBytes("ISO-8859-1"), "UTF-8");
            content = new String(httpServletRequest.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "new_article");
        String fileName = "";
        try {
            Iterator<String> itr = httpServletRequest.getFileNames();
            MultipartFile file = httpServletRequest.getFile(itr.next());
            InputStream inputStream = null;
            OutputStream outputStream = null;
            String[] names = access.getLastImageNumber().toString().split(".jpg");
            fileName = String.valueOf(Integer.valueOf(names[0]) + 1) + ".jpg";

            try {
                inputStream = file.getInputStream();

                File newFile = new File("C:/Users/Natalie_2/Documents/NewsHub/src/main/webapp/images/" + fileName);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

            } catch (IOException e) {
                logger.error("Error occurred in method saveNewArticle() in class AdminController:\n", e);
            } finally {
                inputStream.close();
                outputStream.close();
            }
        } catch (Exception e) {
            logger.error("Error occurred in method saveNewArticle() in class AdminController:\n", e);
        } finally {
        }
        Integer numberOnMain = access.getMaxNumberOnMain() + 1;
        Integer articleId = access.addArticle(title, content, fileName, access.getCurrentUser().getId(), numberOnMain);
        List<Tags> tagsList = (ArrayList<Tags>) httpServletRequest.getSession().getAttribute("new_article_tags");
        if(tagsList != null) {
            for (Tags tags : tagsList) {
                access.addTagToArticle(tags.getId(), articleId);
            }
        }
        logger.info("New article saved successfully in method saveNewArticle() in class AdminController");
        return "redirect:/admin/drafts/page/1";
    }

    @RequestMapping(value = "/edit_article/{id}", method = RequestMethod.GET) //*
    public String editArticle(@PathVariable("id") Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        Articles article = access.getArticle(id);
        String title = article.getTitle();
        String content = article.getContent();
        String image = article.getImage();
        List<Tags> tagsList = access.getTagsByArticleId(id);
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "edit_article");
        modelMap.addAttribute("current_page", "edit_article");
        httpServletRequest.getSession().setAttribute("id", id);
        modelMap.addAttribute("title", title);
        modelMap.addAttribute("content", content);
        modelMap.addAttribute("image", image);
        modelMap.addAttribute("tagsList", tagsList);
        httpServletRequest.getSession().setAttribute("new_article_tags", tagsList);
        modelMap.addAttribute("tags_list", access.getAllTags());
        logger.info("Article edited successfully in method editArticle() in class AdminController");
        return "articles_30";
    }

    @RequestMapping(value = "/save_edited_article", method = RequestMethod.POST) //*
    public String saveEditedArticle(ModelMap modelMap, MultipartHttpServletRequest httpServletRequest) {
        String title = "";
        String content = "";
        try {
            title= new String(httpServletRequest.getParameter("title_text").getBytes("ISO-8859-1"), "UTF-8");
            content = new String(httpServletRequest.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
        logger.info("New article page shown successfully in method showNewArticle() in class AdminController");
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "new_article");
        String fileName = "";
        try {
            Iterator<String> itr = httpServletRequest.getFileNames();
            if(itr != null) {
                MultipartFile file = httpServletRequest.getFile(itr.next());
                InputStream inputStream = null;
                OutputStream outputStream = null;
                String[] names = access.getLastImageNumber().toString().split(".jpg");
                fileName = String.valueOf(Integer.valueOf(names[0]) + 1) + ".jpg";

                try {
                    inputStream = file.getInputStream();

                    File newFile = new File("C:/Users/Natalie_2/Documents/NewsHub/src/main/webapp/images/" + fileName);
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    }
                    outputStream = new FileOutputStream(newFile);
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }

                } catch (IOException e) {
                    logger.error("Error occurred in method saveEditedArticle() in class AdminController:\n", e);
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred in method saveEditedArticle() in class AdminController:\n", e);
        } finally {
        }
        Integer id = (Integer) httpServletRequest.getSession().getAttribute("id");
        access.editArticle(id, title, content, fileName, access.getArticle(id).getNumberOnMain());
        List<Tags> tagsList = (ArrayList<Tags>) httpServletRequest.getSession().getAttribute("tagsList");
        if(tagsList != null) {
            for (Tags tags : tagsList) {
                access.addTagToArticle(tags.getId(), id);
            }
        }
        logger.info("Edited article saved successfully in method saveEditedArticle() in class AdminController");
        return "redirect:/admin/drafts/page/1";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = new Access();
        httpServletRequest.getSession().setAttribute("access", access);
        logger.info("Logout performed successfully in method logout() in class AdminController");
        return "redirect:/";
    }

    @RequestMapping(value = "/save_privileges", method = RequestMethod.POST)
    public String savePrivileges(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        String[] roles = {"admin", "editor", "corrector", "author"};
        String[] privileges = {"AddArticle", "AddArticleToMain", "RemoveArticleFromMain", "EditArticle", "CheckArticle", "ApproveArticle", "ArchiveArticle", "FeatureArticle", "DeleteArticle", "DraftArticle", "RejectArticle", "SetImage", "GetArticle", "GetAllArticles", "GetArticlesByTagId", "GetArticlesTags", "AddTag", "AddTagToArticle", "EditTag", "DeleteTag", "GetTag", "GetAllTags", "GetTagsByArticleId", "GetUserByArticleId", "AddUser", "ChangeUserPrivileges", "DeleteUser", "EditUserInfo", "GetUser", "GetAllUsers", "GetAllPrivileges"};
        HashMap<String, List<Boolean>> privilegesMap = new HashMap<>();
        for (String role : roles) {
            privilegesMap.put(role, new ArrayList<>());
            for (String privilege : privileges) {
                if (httpServletRequest.getParameter(role + "_" + privilege) == null) {
                    privilegesMap.get(role).add(false);
                } else {
                    if (httpServletRequest.getParameter(role + "_" + privilege).equals("on")) {
                        privilegesMap.get(role).add(true);
                    }
                }
            }
        }
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        List<Privileges> privilegesList = new ArrayList<>();
        for (HashMap.Entry<String, List<Boolean>> entry : privilegesMap.entrySet()) {
            Privileges temp = access.getPrivilegeByName(entry.getKey());
            temp.setAddArticle(entry.getValue().get(0));
            temp.setAddArticleToMain(entry.getValue().get(1));
            temp.setRemoveArticleFromMain(entry.getValue().get(2));
            temp.setEditArticle(entry.getValue().get(3));
            temp.setCheckArticle(entry.getValue().get(4));
            temp.setApproveArticle(entry.getValue().get(5));
            temp.setArchiveArticle(entry.getValue().get(6));
            temp.setFeatureArticle(entry.getValue().get(7));
            temp.setDeleteArticle(entry.getValue().get(8));
            temp.setDraftArticle(entry.getValue().get(9));
            temp.setRejectArticle(entry.getValue().get(10));
            temp.setSetImage(entry.getValue().get(11));
            temp.setGetArticle(entry.getValue().get(12));
            temp.setGetAllArticles(entry.getValue().get(13));
            temp.setGetArticlesByTagId(entry.getValue().get(14));
            temp.setGetArticlesTags(entry.getValue().get(15));
            temp.setAddTag(entry.getValue().get(16));
            temp.setAddTagToArticle(entry.getValue().get(17));
            temp.setEditTag(entry.getValue().get(18));
            temp.setDeleteTag(entry.getValue().get(19));
            temp.setGetTag(entry.getValue().get(20));
            temp.setGetAllTags(entry.getValue().get(21));
            temp.setGetTagsByArticleId(entry.getValue().get(22));
            temp.setGetUserByArticleId(entry.getValue().get(23));
            temp.setAddUser(entry.getValue().get(24));
            temp.setChangeUserPrivileges(entry.getValue().get(25));
            temp.setDeleteUser(entry.getValue().get(26));
            temp.setEditUserInfo(entry.getValue().get(27));
            temp.setGetUser(entry.getValue().get(28));
            temp.setGetAllUsers(entry.getValue().get(29));
            temp.setGetAllPrivileges(entry.getValue().get(30));
            privilegesList.add(temp);
        }
        access.setPrivileges(privilegesList);
        logger.info("Privileges saved successfully in method savePrivileges() in class AdminController");
        return "redirect:/admin/redirect_to_page/show_privileges";
    }

    @RequestMapping(value = "/sent_notification", method = RequestMethod.POST) //*
    public String sendNotification(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getParameter("email_for_new_user");
        String privilege_name = httpServletRequest.getParameter("select_privilege");
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.addUser(access.getPrivilegeByName(privilege_name).getId(), "", "", email, "", "");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "show_users");
        modelMap.addAttribute("current_page", "show_users");
        MessagesClass messagesClass = new MessagesClass();
        try {
            messagesClass.sendMessage(email, "registration", "http://button");
        } catch (Exception e) {
            logger.error("Error occurred in method sendNotification() in class AdminController:\n", e);
        }
        logger.info("Notification sent successfully in method sendNotification() in class AdminController");
        return "redirect:/admin/show_users/page/1";
    }

    @ModelAttribute("getPrivileges")
    public List<String> getPrivileges(){
        final List<String> list = new ArrayList<String>() {{
            add("editor");
            add("corrector");
            add("author");
        }};
        logger.info("Privileges got successfully in method getPrivileges() in class AdminController");
        return list;
    }


    @RequestMapping(value = "/feature_article/{id}", method = RequestMethod.GET)
    public String checkArticle(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.featureArticle(id, (access.getArticle(id).getFeatured()) ? false : true);
        logger.info("Article checked successfully in method checkArticle() in class AdminController");
        return "redirect:/admin/redirect_to_page/main_page";
    }

    @RequestMapping(value = "/archive_article/{id}", method = RequestMethod.GET)
    public String archiveArticle(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.archiveArticle(id, (access.getArticle(id).getArchived()) ? false : true);
        Articles article = access.getArticle(id);
        access.editArticle(id, article.getTitle(), article.getContent(), article.getImage(), (access.getArticle(id).getArchived()) ? access.getMaxNumberOnMain() + 1 : null);
        logger.info("Article archived successfully in method archiveArticle() in class AdminController");
        return "redirect:/admin/redirect_to_page/published";
    }

    @RequestMapping(value = "/approve_article/{id}", method = RequestMethod.GET)
    public String approveArticle(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.approveArticle(id, true);
        logger.info("Article approved successfully in method approveArticle() in class AdminController");
        return "redirect:/admin/redirect_to_page/being_processed_by_editor";
    }

    @RequestMapping(value = "/setUser/{user_id}", method = RequestMethod.GET)
    public String setUser(@PathVariable("user_id") Integer userId, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        String privilege_name = httpServletRequest.getParameter("select_privilege");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "show_users");
        modelMap.addAttribute("current_page", "show_users");
        Users user = access.getUser(userId);
        access.editUserInfo(userId, user.getLogin(), user.getPassword(), user.getEmail(), user.getFirstName().toString(), user.getLastName().toString(), access.getPrivilegeByName(privilege_name).getId());
        logger.info("Article approved successfully in method approveArticle() in class AdminController");
        return "redirect:/admin/show_users/page/1";
    }

    @RequestMapping(value = "/move_up/{article_id}", method = RequestMethod.GET)
    public String moveUp(@PathVariable("article_id") Integer articleId, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "main_page");
        modelMap.addAttribute("current_page", "main_page");
        Articles article = access.getArticle(articleId);
        List<ArticlesInfoEntity> articles = getAllArticlesInfoEntity();
        int maxNumberOnMain = access.getMaxNumberOnMain();
        if(article.getNumberOnMain() < maxNumberOnMain) {
            int previousNumber = article.getNumberOnMain();
            for (ArticlesInfoEntity articlesInfoEntity : articles) {
                if(articlesInfoEntity.getArticle().getNumberOnMain() > previousNumber) {
                    access.editArticle(articleId, article.getTitle(), article.getContent(), article.getImage(), articlesInfoEntity.getArticle().getNumberOnMain());
                    access.editArticle(articlesInfoEntity.getArticle().getId(), articlesInfoEntity.getArticle().getTitle(), articlesInfoEntity.getArticle().getContent(), articlesInfoEntity.getArticle().getImage(), previousNumber);
                    return "redirect:/admin/main_page/page/1";
                }
            }
        }
        logger.info("Article approved successfully in method approveArticle() in class AdminController");
        return "redirect:/admin/main_page/page/1";
    }


    @RequestMapping(value = "/move_down/{article_id}", method = RequestMethod.GET)
    public String moveDown(@PathVariable("article_id") Integer articleId, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "main_page");
        modelMap.addAttribute("current_page", "main_page");
        Articles article = access.getArticle(articleId);
        List<ArticlesInfoEntity> articles = getAllArticlesInfoEntityRevertSort();
        int maxNumberOnMain = access.getMaxNumberOnMain();
        if(article.getNumberOnMain() > 1) {
            int previousNumber = article.getNumberOnMain();
            for (ArticlesInfoEntity articlesInfoEntity : articles) {
                if(articlesInfoEntity.getArticle().getNumberOnMain() < previousNumber) {
                    access.editArticle(articleId, article.getTitle(), article.getContent(), article.getImage(), articlesInfoEntity.getArticle().getNumberOnMain());
                    access.editArticle(articlesInfoEntity.getArticle().getId(), articlesInfoEntity.getArticle().getTitle(), articlesInfoEntity.getArticle().getContent(), articlesInfoEntity.getArticle().getImage(), previousNumber);
                    return "redirect:/admin/main_page/page/1";
                }
            }
        }
        logger.info("Article approved successfully in method approveArticle() in class AdminController");
        return "redirect:/admin/main_page/page/1";
    }

    @RequestMapping(value = "/save_new_user", method = RequestMethod.POST) //*
    public String saveNewUser(ModelMap modelMap, MultipartHttpServletRequest httpServletRequest) {
        String login = "";
        String password = "";
        String email = "";
        String first_name = "";
        String last_name = "";
        String rights = "";
        try {
            login = new String(httpServletRequest.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
            password = new String(httpServletRequest.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");
            email = new String(httpServletRequest.getParameter("email").getBytes("ISO-8859-1"), "UTF-8");
            first_name = new String(httpServletRequest.getParameter("first_name").getBytes("ISO-8859-1"), "UTF-8");
            last_name = new String(httpServletRequest.getParameter("last_name").getBytes("ISO-8859-1"), "UTF-8");
            rights = new String(httpServletRequest.getParameter("select_privilege").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("current_nav", "show_users");
        modelMap.addAttribute("current_page", "show_users");
        access.addUser(access.getPrivilegeByName(rights).getId(), login, password, email, first_name, last_name);
        logger.info("New article saved successfully in method saveNewArticle() in class AdminController");
        return "redirect:/admin/show_users/page/1";
    }
}