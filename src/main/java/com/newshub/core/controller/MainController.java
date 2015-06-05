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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalie on 17.04.2015.
 */
@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class);

    private List<ArticlesInfoEntity> list;

    @RequestMapping(method = {RequestMethod.GET})
    public String showMainPage(ModelMap modelMap) {
        logger.info("Main page shown successfully in class MainController");
        return "redirect:/connect";
        /*return "redirect:/pagination/page/1";*/
        /*return "index_1";*/
    }


/*    @ModelAttribute("access")
    public Access getAccess(){
        ApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
        Access access = (Access)context.getBean("accessBean");
        return access;
    }*/

    @ModelAttribute("articlesList")
    public List<Articles> getArticlesList() {
        List<Articles> listOfArticles = new Access().getAllArticles();
        List<Articles> approvedArticles = new ArrayList<Articles>();
        for (Articles listOfArticle : listOfArticles) {
            if (listOfArticle.getApproved() && !listOfArticle.getArchived()) {
                approvedArticles.add(listOfArticle);
            }
        }
        logger.info("ArticlesList got successfully in method getArticlesList() in class MainController");
        return approvedArticles;
    }

/*    @ModelAttribute("pagination")
    public Pagination paginationShow() {
        return new Pagination(1, 1,1, new PageImpl<ArticlesInfoEntity>(getAllArticlesInfoEntity()));
    }*/

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
        return "index_1";
    }

/*    @RequestMapping(method = RequestMethod.GET)
    public String bookingsRedirect(){
        return "redirect:/page/1";
    }*/

/*
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public String awesomeThings(@PathVariable Integer pageNumber,Model uiModel){

        PageRequest newPgb = new PageRequest(pageNumber - 1, 20);

        Page<ArticlesInfoEntity> currentResults = new PageImpl<ArticlesInfoEntity>(list, newPgb, 1);
        //Pagination variables
        int currentIndex = currentResults.getNumber() + 1;
        int begin = Math.max(1, currentIndex - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); // how many pages to display in the pagination bar
        Pagination pagination = new Pagination(currentIndex, begin, end, currentResults);
        uiModel.addAttribute("pagination", pagination);
        return "index_1";
    }
*/

  /*  @ModelAttribute("page")
    public PageWrapper<ArticlesInfoEntity> blog(Model uiModel, Pageable pageable) {
        Page<ArticlesInfoEntity> blogList = new PageImpl<ArticlesInfoEntity>(getAllArticlesInfoEntity());
        PageWrapper<ArticlesInfoEntity> page = new PageWrapper<ArticlesInfoEntity>
                    (blogList, "/blog");
        return page;
    }
*/
    @ModelAttribute("tagsList")
    public List<Tags> getTagsList() {
        logger.info("List of tags got successfully in method getTagsList() in class MainController");
        return new Access().getAllTags();
    }
}