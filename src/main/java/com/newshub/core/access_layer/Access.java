package com.newshub.core.access_layer;

import com.newshub.core.domain.*;
import com.newshub.core.services.ArticlesServices;
import com.newshub.core.services.CustomServices;
import com.newshub.core.services.TagsServices;
import com.newshub.core.services.UsersServices;
import com.newshub.core.utils.HibernateUtils;
import com.newshub.core.utils.Tabs;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Natalie on 25.04.2015.
 */
public class Access implements Serializable {
    private Session session;
    private ArticlesServices articlesServices;
    private TagsServices tagsServices;
    private UsersServices usersServices;
    private CustomServices customServices;
    private Users user;
    private Privileges privilege;
    private Tabs tabs;

    public Access() {
        session = new HibernateUtils().getSession();
        articlesServices = new ArticlesServices(session);
        tagsServices = new TagsServices(session);
        usersServices = new UsersServices(session);
        customServices = new CustomServices(session);
        privilege = new Privileges();
        privilege.setGetAllArticles(true);
        privilege.setGetAllTags(true);
        privilege.setGetArticlesTags(true);
        privilege.setGetTagsByArticleId(true);
        privilege.setGetUserByArticleId(true);
        privilege.setGetAllUsers(true);
        privilege.setGetArticlesByTagId(true);
        privilege.setGetArticle(true);
    }

    public Tabs getTabs() {
        return tabs;
    }

    public void setTabs(Tabs tabs) {
        this.tabs = tabs;
    }

    public Users getCurrentUser() {
        return user;
    }

    public Privileges getCurrentPrivilege() {
        return privilege;
    }

    public boolean connect(String login, String password) {

        for (Users tempUser : usersServices.getAllUsers()) {
            if (tempUser.getLogin().equals(login) && tempUser.getPassword().equals(password)) {
                this.user = tempUser;
                privilege = user.getPrivilegesByPrivilegeId();
                return true;
            }
        }
        return false;
    }

    public Integer addArticle(String title, String content, String img, Integer userId, Integer numberOnMain) {
        if (privilege.getAddArticle()) {
            return articlesServices.addArticle(title, content, img, userId, numberOnMain);
        }
        return 0;
    }

    public void editArticle(Integer id, String title, String content, String img, Integer numberOnMain) {
        if (privilege.getEditArticle()) {
            articlesServices.editArticle(id, title, content, img, numberOnMain);
        }
    }

    public void checkArticle(int id, Boolean flag) {
        if (privilege.getCheckArticle()) {
            articlesServices.checkArticle(id, flag);
        }
    }

    public void approveArticle(int id, Boolean flag) {
        if (privilege.getApproveArticle()) {
            articlesServices.approveArticle(id, flag);
        }
    }

    public void archiveArticle(int id, Boolean flag) {
        if (privilege.getArchiveArticle()) {
            articlesServices.archiveArticle(id, flag);
        }
    }

    public void featureArticle(int id, Boolean flag) {
        if (privilege.getFeatureArticle()) {
            articlesServices.featureArticle(id, flag);
        }
    }

    public void deleteArticle(int id) {
        if (privilege.getDeleteArticle()) {
            articlesServices.deleteArticle(id);
        }
    }

    public Articles getArticle(int id) {
        if (privilege.getGetArticle()) {
            return articlesServices.getArticle(id);
        }
        return null;
    }

    public List<Articles> getAllArticles() {
        if (privilege.getGetAllArticles()) {
            return articlesServices.getAllArticles();
        }
        return null;
    }

    public void draftArticle(int id, Boolean flag) {
        if (privilege.getDraftArticle()) {
            articlesServices.draftArticle(id, flag);
        }
    }

    public void rejectArticle(int id, Boolean flag) {
        if (privilege.getRejectArticle()) {
            articlesServices.rejectArticle(id, flag);
        }
    }

    public void addImageToArticle(int id, String imageName) {
        if (privilege.getRejectArticle()) {
            articlesServices.addImageToArticle(id, imageName);
        }
    }

    public String getImage(int id) {
        if (privilege.getSetImage()) {
            return articlesServices.getImage(id);
        }
        return "";
    }

    public void addTag(int id, String name) {
        if (privilege.getAddTag()) {
            tagsServices.addTag(id, name);
        }
    }

    public void addTagToArticle(int tagId, int articleId) {
        if (privilege.getAddTagToArticle()) {
            tagsServices.addTagToArticle(tagId, articleId);
        }
    }

    public void editTag(int tagId, String tagName) {
        if (privilege.getEditTag()) {
            tagsServices.editTag(tagId, tagName);
        }
    }

    public void deleteTag(int tagId, int articleId) {
        if (privilege.getDeleteTag()) {
            tagsServices.deleteTag(tagId, articleId);
        }
    }

    public Tags getTag(int id) {
        if (privilege.getGetTag()) {
            return tagsServices.getTag(id);
        }
        return null;
    }

    public List<Tags> getAllTags() {
        if (privilege.getGetAllTags()) {
            return tagsServices.getAllTags();
        }
        return null;
    }

    public List<Articles> getArticlesByTagId(int tagId) {
        if (privilege.getGetArticlesByTagId()) {
            return tagsServices.getArticlesByTagId(tagId);
        }
        return null;
    }

    public List<ArticlesTags> getArticlesTags (){
        if (privilege.getGetArticlesTags()) {
            return articlesServices.getArticlesTags();
        }
        return null;
    }

    public List<Tags> getTagsByArticleId (int articleId){
        if (privilege.getGetTagsByArticleId()) {
            return tagsServices.getTagsByArticleId(articleId);
        }
        return null;
    }

    public void addUser(int privilegeId, String login, String password, String email, String firstName, String lastName) {
        if (privilege.getAddUser()) {
            usersServices.addUser(privilegeId, login, password, email, firstName, lastName);
        }
    }

    public void changeUserPrivileges(int privilegesId, int id) {
        if (privilege.getChangeUserPrivileges()) {
            usersServices.changeUserPrivileges(privilegesId, id);
        }
    }

    public void deleteUser(int id) {
        if (privilege.getDeleteUser()) {
            usersServices.deleteUser(id);
        }
    }

    public void editUserInfo(int id, String login, String password, String email, String firstName, String lastName, Integer privilegeId) {
        if (privilege.getEditUserInfo()) {
            usersServices.editUserInfo(id, login, password, email, firstName, lastName, privilegeId);
        }
    }

    public Users getUser(int id) {
        if (privilege.getGetUser()) {
            return usersServices.getUser(id);
        }
        return null;
    }

    public List<Users> getAllUsers() {
        if (privilege.getGetAllUsers()) {
            return usersServices.getAllUsers();
        }
        return null;
    }

    public List<Privileges> getAllPrivileges() {
        if (privilege.getGetAllPrivileges()) {
            return usersServices.getAllPrivileges();
        }
        return null;
    }

    public Users getUserByArticleId (int articleId){
        if (privilege.getGetUserByArticleId()) {
            return usersServices.getUserByArticleId(articleId);
        }
        return null;
    }

    public List<Integer> getSearchedArticles(String title) {
        return customServices.getSearchedArticles(title);
    }

    public Integer getMaxNumberOnMain() {
        return customServices.getMaxNumberOnMain();
    }

    public String getLastImageNumber() {
        return customServices.getLastImageNumber();
    }

    public void setPrivileges(List<Privileges> list) {
        usersServices.setPrivileges(list);
    }

    public Privileges getPrivilegeByName(String name){
        return usersServices.getPrivilegeByName(name);
    }

    public void close() {
        user = null;
        privilege = null;
    }

}
