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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie on 17.04.2015.
 */
@Controller
@SessionAttributes("accessAtribute")
@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = {RequestMethod.GET})
    public String showMainPage(ModelMap modelMap) {
        logger.info("Main page shown successfully in class MainController");
        /*return "redirect:/connect";*/
        return "redirect:/pagination/page/1";
    }

    @ModelAttribute("articlesInfo")
    public List<ArticlesInfoEntity> getArticlesInfo() {
        logger.info("Articles info got successfully in method getArticlesInfo() in class MainController");
        return getAllArticlesInfoEntity();
    }

    public List<ArticlesInfoEntity> getAllArticlesInfoEntity() {
        Access access = new Access();
        List<Articles> list = access.getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : list) {
            if (listOfArticle.getApproved() && !listOfArticle.getArchived()) {
                listOfArticle.setContent(listOfArticle.getContent().substring(0, (listOfArticle.getContent().length() > 100) ? 100 : (listOfArticle.getContent().length()) - 1));
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
        logger.info("AllArticlesInfoEntity got successfully in method getAllArticlesInfoEntity() in class MainController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/pagination/page/{pageNumber}", method = RequestMethod.GET)
    public String pages(@PathVariable Integer pageNumber, Model model) {
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
        model.addAttribute("current_nav", "articles");
        model.addAttribute("current_page", "articles");
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
        logger.info("Pagination performed successfully in method pages()in class MainController");
        return "index_1";
    }

    @ModelAttribute("tagsList")
    public List<Tags> getTagsListForMain() {
        List<Tags> tagsListForMain = new Access().getAllTags();
        tagsListForMain = tagsListForMain.subList(0, (tagsListForMain.size() < 11) ? (tagsListForMain.size()) : 9);
        logger.info("List of tags for main page got successfully in method getTagsListForMain() in class MainController");
        return tagsListForMain;
    }

    public List<Tags> getAllTags(Access access) {
        logger.info("List of all tags got successfully in method getAllTags() in class MainController");
        return access.getAllTags();
    }

    @RequestMapping(value = "/tags/page/{pageNumber}", method = RequestMethod.GET)
    public String showTags(@PathVariable Integer pageNumber, Model model) {
        Access access = new Access();
        List<Tags> tagsList = getAllTags(access);
        List<List<Tags>> listOfTagsLists = new ArrayList<List<Tags>>();
        int count = 1;
        List<Tags> tempList = new ArrayList<Tags>();
        for(int i = 0; i < tagsList.size(); i++){
            if(count == 100 || (i == tagsList.size() - 1)){
                tempList.add(tagsList.get(i));
                listOfTagsLists.add(tempList);
                tempList = new ArrayList<Tags>();
                count=0;
            }else{
                tempList.add(tagsList.get(i));
            }
            ++count;
        }
        List<Integer> listOfPagesNumbers = new ArrayList<Integer>();
        model.addAttribute("tags_groups", tagsList);
        model.addAttribute("tagsInfo", listOfTagsLists.get(pageNumber - 1));
        for(int i = 0; i < listOfTagsLists.size(); i++) {
            listOfPagesNumbers.add(i + 1);
        }
        model.addAttribute("pages", listOfPagesNumbers);
        model.addAttribute("active_page", pageNumber);
        model.addAttribute("max_page", listOfPagesNumbers.size());
        model.addAttribute("current_nav", "tags");
        model.addAttribute("current_page", "tags");
        model.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        model.addAttribute("access", access);
        logger.info("Tags shown successfully in method showTags() in class MainController");
        return "index_1";
    }



    @RequestMapping(value = "/articles_by_tag/page/{id}", method = RequestMethod.GET)
    public String showArticlesByTag(@PathVariable("id")  Integer id, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Access access = new Access();
        List<ArticlesInfoEntity> articlesInfoEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : access.getArticlesByTagId(id)) {
            if(article.getApproved() && !article.getArchived()) {
                ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
                article.setContent(article.getContent().substring(0, (article.getContent().length() > 100) ? 100 : (article.getContent().length()) - 1));
                articlesInfoEntity.setArticle(article);
                List<Tags> tags = access.getTagsByArticleId(article.getId());
                articlesInfoEntity.setTags(tags);
                articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
                articlesInfoEntities.add(articlesInfoEntity);
            }
        }
        httpServletRequest.getSession().setAttribute("articlesInfo", articlesInfoEntities);
        modelMap.addAttribute("current_nav", "articles_by_tag");
        modelMap.addAttribute("current_privilege", access.getCurrentPrivilege().getName());
        modelMap.addAttribute("access", access);
        logger.info("List of articles by tag shown successfully in method showArticlesByTag() in class MainController");
        return "redirect:/articles_by_tag_pagination/page/1";
    }

    @RequestMapping(value = "/articles_by_tag_pagination/page/{pageNumber}", method = RequestMethod.GET)
    public String articlesByTagPagination(@PathVariable Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        List<ArticlesInfoEntity> allEntities = (List<ArticlesInfoEntity>)httpServletRequest.getSession().getAttribute("articlesInfo");
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
        model.addAttribute("current_nav", "articles_by_tag");
        model.addAttribute("current_page", "articles_by_tag");
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
        logger.info("Pagination performed successfully in method articlesByTagPagination() in class MainController");
        return "index_1";
    }

    @RequestMapping(value = "/article_page/{id}", method = RequestMethod.GET)
    public String showArticlePage(@PathVariable Integer id, Model model, HttpServletRequest httpServletRequest) {

        Access access = new Access();
        Articles article = access.getArticle(id);
        ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
        articlesInfoEntity.setArticle(article);
        List<Tags> tags = access.getTagsByArticleId(article.getId());
        articlesInfoEntity.setTags(tags);
        articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));

        model.addAttribute("article", articlesInfoEntity);
        model.addAttribute("current_nav", "one_article");
        model.addAttribute("current_page", "one_article");
        logger.info("Article page shown successfully in method showArticlePage() in class MainController");
        return "index_1";
    }

    @RequestMapping(value = "/getPrivelege", method = RequestMethod.GET)
    public String show(@PathVariable Integer id, Model model, HttpServletRequest httpServletRequest) {
        Access access = (Access) httpServletRequest.getSession().getAttribute("access");
        logger.info("Method show() performed successfully in class MainController");
        return access.getCurrentPrivilege().getName();
    }

    @RequestMapping(value = "/show_articles_by_search/page/{pageNumber}", method = RequestMethod.POST)
    public String showArticlesBySearch(@PathVariable("pageNumber") Integer pageNumber, Model model, HttpServletRequest httpServletRequest) {
        String text = null;
        try {
            text = new String(httpServletRequest.getParameter("text_for_search").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Access access = new Access();
        List<Integer> list = access.getSearchedArticles(text);
        List<Articles> articles = new ArrayList<Articles>();
        for (Integer integer : list) {
            articles.add(access.getArticle(integer));
        }

        List<ArticlesInfoEntity> allEntities = new ArrayList<ArticlesInfoEntity>();
        for (Articles article : articles) {
            ArticlesInfoEntity articlesInfoEntity = new ArticlesInfoEntity();
            article.setContent(article.getContent().substring(0, (article.getContent().length() > 100) ? 100 : (article.getContent().length()) - 1));
            articlesInfoEntity.setArticle(article);
            List<Tags> tags = access.getTagsByArticleId(article.getId());
            articlesInfoEntity.setTags(tags);
            articlesInfoEntity.setUser(access.getUserByArticleId(article.getId()));
            allEntities.add(articlesInfoEntity);
        }

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
        model.addAttribute("current_nav", "search_articles");
        model.addAttribute("current_page", "search_articles");
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
        logger.info("All articles by search shown successfully in method showArticlesBySearch) in class MainController");
        return "index_1";
    }

    public List<ArticlesInfoEntity> getAllArchivedArticleInfoEntity() {
        Access access = new Access();
        List<Articles> list = access.getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : list) {
            if (listOfArticle.getArchived()) {
                listOfArticle.setContent(listOfArticle.getContent().substring(0, (listOfArticle.getContent().length() > 100) ? 100 : (listOfArticle.getContent().length()) - 1));
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
        logger.info("AllArticledInfoEntity got successfully in method getAllArticledInfoEntity() in class MainController");
        return articlesInfoEntities;
    }

    @RequestMapping(value = "/show_archives/page/{pageNumber}", method = RequestMethod.GET)
    public String showArchived(@PathVariable Integer pageNumber, Model model) {
        List<ArticlesInfoEntity> allEntities = getAllArchivedArticleInfoEntity();
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
        model.addAttribute("current_nav", "archive");
        model.addAttribute("current_page", "archive");
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
        logger.info("List of all archived articles shown successfully in method showArchived() in class MainController");
        return "index_1";
    }
}