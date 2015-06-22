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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Natalie_2 on 5/18/2015.
 */

@Controller
@SessionAttributes("accessAtribute")
@RequestMapping("/editor")
public class EditorController {

    private Logger logger = Logger.getLogger(EditorController.class);

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String showEditorPage(ModelMap model, @ModelAttribute("access") Access access, HttpServletRequest request) {
        if(request.getSession().getAttribute("access") == null) request.getSession().setAttribute("access", access);
        logger.info("Editor page shown successfully in method showEditorPage() in class EditorController");
        return "redirect:/editor/drafts/page/1";
    }

    @RequestMapping(value = "/redirect_to_page/{page}", method = RequestMethod.GET)
    public String pagesRedirect(@PathVariable String page, Model model) {
        logger.info("Page redirect performed successfully in method pagesRedirect() in class EditorController");
        return "redirect:/editor/" + page + "/page/1";
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
        logger.info("List of ArticlesInfoEntities got successfully in method getAllArticlesInfoEntity() in class EditorController");
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
        model.addAttribute("articlesInfo", (allArticles.size() > 0) ? allArticles.get(pageNumber - 1) : new ArrayList<ArticlesInfoEntity>());
        if(allArticles.size() > 0) {
            for (int i = 0; i < allArticles.size(); i++) {
                listOfPagesNumbers.add(i + 1);
            }
        }
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        logger.info("Pagination performed successfully in method pages() in class EditorController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_tags", method = {RequestMethod.GET})
    public String showAllTags (ModelMap model, HttpServletRequest httpServletRequest){
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);
        model.addAttribute("current_nav", "tags");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("tagsList", access.getAllTags());
        logger.info("All tags shown successfully in method showAllTags() in class EditorController");
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
        modelMap.addAttribute("access", access);
        logger.info("List of all articles by tag shows successfully in method showArticlesByTag() in class EditorController");
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
        logger.info("List of all drafts got successfully in method getAllDrafts() in class EditorController");
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
        model.addAttribute("current_page", "drafts");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("Drafts shown successfully in method showDrafts() in class EditorController");
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
        logger.info("List of all articles being processed by editor got successfully in method getAllBeingProcessedByEditor() in class EditorController");
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
        model.addAttribute("current_page", "being_processed_by_editor");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);  // change
        logger.info("All articles being processed by editor shown successfully in method showBeingProcessedByEditor() in class EditorController");
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
        logger.info("List of all checked articles got successfully in method getAllChecked() in class EditorController");
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
        logger.info("Checked articles shown successfully in method showChecked() in class EditorController");
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
        logger.info("List of all published articles got successfully in method getAllPublished() in class EditorController");
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
        model.addAttribute("current_page", "published");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());

        model.addAttribute("access", access);  // change
        logger.info("All published articles shown successfully in method showPublished() in class EditorController");
        return "articles_30";
    }

    @RequestMapping(value = "/show_new_article", method = RequestMethod.GET)
    public String showNewArticle(ModelMap model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        model.addAttribute("access", access);  // change
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("current_nav", "new_article");
        model.addAttribute("tags_list", access.getAllTags());
        logger.info("New article shown successfully in method showNewArticle() in class EditorController");
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
        logger.info("Chosen tag saved successfully in method saveChosenTag() in class EditorController");
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
                logger.error("Error occurred in method saveNewArticle() in class EditorController:\n", e);
            } finally {
                inputStream.close();
                outputStream.close();
            }
        } catch (Exception e) {
            logger.error("Error occurred in method saveNewArticle() in class EditorController:\n", e);
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
        logger.info("New article saved successfully in method saveNewArticle() in class EditorController");
        return "redirect:/admin/editor/page/1";
    }

    @RequestMapping (value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap modelMap, HttpServletRequest httpServletRequest){
        Access access = new Access();
        httpServletRequest.getSession().setAttribute("access", access);
        logger.info("Logout performed successfully in method logout() in class EditorController");
        return "redirect:/";
    }

    @RequestMapping(value = "/approve_article/{id}", method = RequestMethod.GET)
    public String approveArticle(@PathVariable Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        access.approveArticle(id, true);
        logger.info("Article approved successfully in method approveArticle() in class EditorController");
        return "redirect:/editor/redirect_to_page/being_processed_by_editor";
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
        logger.info("Article edited successfully in method editArticle() in class EditorController");
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
                    logger.error("Error occurred in method saveEditedArticle() in class EditorController:\n", e);
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred in method saveEditedArticle() in class EditorController:\n", e);
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
        logger.info("Edited article saved successfully in method saveEditedArticle() in class EditorController");
        return "redirect:/editor/drafts/page/1";
    }

}