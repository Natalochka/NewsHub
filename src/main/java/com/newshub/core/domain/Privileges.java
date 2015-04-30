package com.newshub.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Natalie_2 on 4/29/2015.
 */
@Entity
public class Privileges implements HibernateEntity {
    private int id;

    @Id
    @javax.persistence.Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Basic
    @javax.persistence.Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean addArticle;

    @Basic
    @javax.persistence.Column(name = "addArticle")
    public boolean isAddArticle() {
        return addArticle;
    }

    public void setAddArticle(boolean addArticle) {
        this.addArticle = addArticle;
    }

    private boolean addArticleToMain;

    @Basic
    @javax.persistence.Column(name = "addArticleToMain")
    public boolean isAddArticleToMain() {
        return addArticleToMain;
    }

    public void setAddArticleToMain(boolean addArticleToMain) {
        this.addArticleToMain = addArticleToMain;
    }

    private boolean removeArticleFromMain;

    @Basic
    @javax.persistence.Column(name = "removeArticleFromMain")
    public boolean isRemoveArticleFromMain() {
        return removeArticleFromMain;
    }

    public void setRemoveArticleFromMain(boolean removeArticleFromMain) {
        this.removeArticleFromMain = removeArticleFromMain;
    }

    private boolean editArticle;

    @Basic
    @javax.persistence.Column(name = "editArticle")
    public boolean isEditArticle() {
        return editArticle;
    }

    public void setEditArticle(boolean editArticle) {
        this.editArticle = editArticle;
    }

    private boolean publishArticle;

    @Basic
    @javax.persistence.Column(name = "publishArticle")
    public boolean isPublishArticle() {
        return publishArticle;
    }

    public void setPublishArticle(boolean publishArticle) {
        this.publishArticle = publishArticle;
    }

    private boolean archiveArticle;

    @Basic
    @javax.persistence.Column(name = "archiveArticle")
    public boolean isArchiveArticle() {
        return archiveArticle;
    }

    public void setArchiveArticle(boolean archiveArticle) {
        this.archiveArticle = archiveArticle;
    }

    private boolean featureArticle;

    @Basic
    @javax.persistence.Column(name = "featureArticle")
    public boolean isFeatureArticle() {
        return featureArticle;
    }

    public void setFeatureArticle(boolean featureArticle) {
        this.featureArticle = featureArticle;
    }

    private boolean deleteArticle;

    @Basic
    @javax.persistence.Column(name = "deleteArticle")
    public boolean isDeleteArticle() {
        return deleteArticle;
    }

    public void setDeleteArticle(boolean deleteArticle) {
        this.deleteArticle = deleteArticle;
    }

    private boolean getArticle;

    @Basic
    @javax.persistence.Column(name = "getArticle")
    public boolean isGetArticle() {
        return getArticle;
    }

    public void setGetArticle(boolean getArticle) {
        this.getArticle = getArticle;
    }

    private boolean addTag;

    @Basic
    @javax.persistence.Column(name = "addTag")
    public boolean isAddTag() {
        return addTag;
    }

    public void setAddTag(boolean addTag) {
        this.addTag = addTag;
    }

    private boolean editTag;

    @Basic
    @javax.persistence.Column(name = "editTag")
    public boolean isEditTag() {
        return editTag;
    }

    public void setEditTag(boolean editTag) {
        this.editTag = editTag;
    }

    private boolean deleteTag;

    @Basic
    @javax.persistence.Column(name = "deleteTag")
    public boolean isDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(boolean deleteTag) {
        this.deleteTag = deleteTag;
    }

    private boolean getTag;

    @Basic
    @javax.persistence.Column(name = "getTag")
    public boolean isGetTag() {
        return getTag;
    }

    public void setGetTag(boolean getTag) {
        this.getTag = getTag;
    }

    private boolean addUser;

    @Basic
    @javax.persistence.Column(name = "addUser")
    public boolean isAddUser() {
        return addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    private boolean changeUserPrivileges;

    @Basic
    @javax.persistence.Column(name = "changeUserPrivileges")
    public boolean isChangeUserPrivileges() {
        return changeUserPrivileges;
    }

    public void setChangeUserPrivileges(boolean changeUserPrivileges) {
        this.changeUserPrivileges = changeUserPrivileges;
    }

    private boolean deleteUser;

    @Basic
    @javax.persistence.Column(name = "deleteUser")
    public boolean isDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    private boolean editUserInfo;

    @Basic
    @javax.persistence.Column(name = "editUserInfo")
    public boolean isEditUserInfo() {
        return editUserInfo;
    }

    public void setEditUserInfo(boolean editUserInfo) {
        this.editUserInfo = editUserInfo;
    }

    private boolean getUser;

    @Basic
    @javax.persistence.Column(name = "getUser")
    public boolean isGetUser() {
        return getUser;
    }

    public void setGetUser(boolean getUser) {
        this.getUser = getUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privileges that = (Privileges) o;

        if (id != that.id) return false;
        if (addArticle != that.addArticle) return false;
        if (addArticleToMain != that.addArticleToMain) return false;
        if (removeArticleFromMain != that.removeArticleFromMain) return false;
        if (editArticle != that.editArticle) return false;
        if (publishArticle != that.publishArticle) return false;
        if (archiveArticle != that.archiveArticle) return false;
        if (featureArticle != that.featureArticle) return false;
        if (deleteArticle != that.deleteArticle) return false;
        if (getArticle != that.getArticle) return false;
        if (addTag != that.addTag) return false;
        if (editTag != that.editTag) return false;
        if (deleteTag != that.deleteTag) return false;
        if (getTag != that.getTag) return false;
        if (addUser != that.addUser) return false;
        if (changeUserPrivileges != that.changeUserPrivileges) return false;
        if (deleteUser != that.deleteUser) return false;
        if (editUserInfo != that.editUserInfo) return false;
        if (getUser != that.getUser) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (addArticle ? 1 : 0);
        result = 31 * result + (addArticleToMain ? 1 : 0);
        result = 31 * result + (removeArticleFromMain ? 1 : 0);
        result = 31 * result + (editArticle ? 1 : 0);
        result = 31 * result + (publishArticle ? 1 : 0);
        result = 31 * result + (archiveArticle ? 1 : 0);
        result = 31 * result + (featureArticle ? 1 : 0);
        result = 31 * result + (deleteArticle ? 1 : 0);
        result = 31 * result + (getArticle ? 1 : 0);
        result = 31 * result + (addTag ? 1 : 0);
        result = 31 * result + (editTag ? 1 : 0);
        result = 31 * result + (deleteTag ? 1 : 0);
        result = 31 * result + (getTag ? 1 : 0);
        result = 31 * result + (addUser ? 1 : 0);
        result = 31 * result + (changeUserPrivileges ? 1 : 0);
        result = 31 * result + (deleteUser ? 1 : 0);
        result = 31 * result + (editUserInfo ? 1 : 0);
        result = 31 * result + (getUser ? 1 : 0);
        return result;
    }
}
