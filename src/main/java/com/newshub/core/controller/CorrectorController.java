package com.newshub.core.controller;

import com.newshub.core.access_layer.Access;
import com.newshub.core.domain.Articles;
import com.newshub.core.domain.Tags;
import com.newshub.core.utils.ArticlesInfoEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
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
        if(request.getSession().getAttribute("access") == null) request.getSession().setAttribute("access", access);
        return "redirect:/corrector/being_processed_by_corrector/page/1"; //?
    }

    @RequestMapping(value = "/redirect_to_page/{page}", method = RequestMethod.GET)
    public String pagesRedirect(@PathVariable String page, ModelMap modelMap) {
        logger.info("Page redirect performed successfully in method pagesRedirect() in class CorrectorController");
        return "redirect:/corrector/" + page + "/page/1"; //?
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
        logger.info("List of all checked articles got successfully in method getAllChecked() in class CorrectorController");
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
        if(allArticles.size() > 0) {
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
        logger.info("Checked articles shown successfully in method showChecked() in class CorrectorController");
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
        logger.info("List of all articles being processed by corrector got successfully in method getAllBeingProcessedByCorrector() in class CorrectorController");
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
        logger.info("All articles being processed by corrector shown successfully in method showBeingProcessedByCorrector() in class CorrectorController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_tags", method = {RequestMethod.GET})
    public String showAllTags(ModelMap model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);
        model.addAttribute("current_nav", "tags");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("tagsList", access.getAllTags());
        logger.info("All tags shown successfully in method showAllTags() in class CorrectorController");
        return "articles_30";
    }

    @RequestMapping(value = "/check_article/{id}", method = RequestMethod.GET)
    public String checkArticle(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.checkArticle(id, true);
        access.draftArticle(id, false);
        logger.info("Article checked successfully in method checkArticle() in class CorrectorController");
        return "redirect:/corrector/redirect_to_page/being_processed_by_corrector";
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
        modelMap.addAttribute("access", access);
        logger.info("List of all articles by tag shown successfully in method showArticlesByTag() in class CorrectorController");
        return "articles_30";
    }

    @RequestMapping (value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap modelMap, HttpServletRequest httpServletRequest){
        Access access = new Access();
        httpServletRequest.getSession().setAttribute("access", access);
        logger.info("Logout performed successfully in method logout() in class CorrectorController");
        return "redirect:/";
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
        logger.info("Article edited successfully in method editArticle() in class CorrectorController");
        return "articles_30";
    }

    @RequestMapping(value = "/save_edited_article", method = RequestMethod.POST) //*
    public String saveEditedArticle(ModelMap modelMap, MultipartHttpServletRequest httpServletRequest) {
        String title = httpServletRequest.getParameter("title_text");
        String content = httpServletRequest.getParameter("content");
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        modelMap.addAttribute("access", access);  // change
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
                    logger.error("Error occurred in method saveEditedArticle() in class CorrectorController:\n", e);
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred in method saveEditedArticle() in class CorrectorController:\n", e);
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
        logger.info("Edited article saved successfully in method saveEditedArticle() in class CorrectorController");
        return "redirect:/corrector/being_processed_by_corrector/page/1";
    }
}