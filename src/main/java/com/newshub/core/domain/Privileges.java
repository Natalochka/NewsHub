package com.newshub.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Natalie_2 on 5/3/2015.
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
    public boolean getAddArticle() {
        return addArticle;
    }

    public void setAddArticle(boolean addArticle) {
        this.addArticle = addArticle;
    }

    private boolean editArticle;

    @Basic
    @javax.persistence.Column(name = "editArticle")
    public boolean getEditArticle() {
        return editArticle;
    }

    public void setEditArticle(boolean editArticle) {
        this.editArticle = editArticle;
    }

    private boolean checkArticle;

    @Basic
    @javax.persistence.Column(name = "checkArticle")
    public boolean getCheckArticle() {
        return checkArticle;
    }

    public void setCheckArticle(boolean checkArticle) {
        this.checkArticle = checkArticle;
    }


    private boolean approveArticle;

    @Basic
    @javax.persistence.Column(name = "approveArticle")
    public boolean getApproveArticle() {
        return approveArticle;
    }

    public void setApproveArticle(boolean approveArticle) {
        this.approveArticle = approveArticle;
    }

    private boolean archiveArticle;

    @Basic
    @javax.persistence.Column(name = "archiveArticle")
    public boolean getArchiveArticle() {
        return archiveArticle;
    }

    public void setArchiveArticle(boolean archiveArticle) {
        this.archiveArticle = archiveArticle;
    }

    private boolean featureArticle;

    @Basic
    @javax.persistence.Column(name = "featureArticle")
    public boolean getFeatureArticle() {
        return featureArticle;
    }

    public void setFeatureArticle(boolean featureArticle) {
        this.featureArticle = featureArticle;
    }

    private boolean deleteArticle;

    @Basic
    @javax.persistence.Column(name = "deleteArticle")
    public boolean getDeleteArticle() {
        return deleteArticle;
    }

    public void setDeleteArticle(boolean deleteArticle) {
        this.deleteArticle = deleteArticle;
    }

    private boolean getArticle;

    @Basic
    @javax.persistence.Column(name = "getArticle")
    public boolean getGetArticle() {
        return getArticle;
    }

    public void setGetArticle(boolean getArticle) {
        this.getArticle = getArticle;
    }

    private boolean getAllArticles;

    @Basic
    @javax.persistence.Column(name = "getAllArticles")
    public boolean getGetAllArticles() {
        return getAllArticles;
    }

    public void setGetAllArticles(boolean getAllArticles) {
        this.getAllArticles = getAllArticles;
    }

    private boolean addTag;

    @Basic
    @javax.persistence.Column(name = "addTag")
    public boolean getAddTag() {
        return addTag;
    }

    public void setAddTag(boolean addTag) {
        this.addTag = addTag;
    }


    private boolean addTagToArticle;

    @Basic
    @javax.persistence.Column(name = "addTagToArticle")
    public boolean getAddTagToArticle() {
        return addTagToArticle;
    }

    public void setAddTagToArticle(boolean addTagToArticle) {
        this.addTagToArticle = addTag;
    }



    private boolean editTag;

    @Basic
    @javax.persistence.Column(name = "editTag")
    public boolean getEditTag() {
        return editTag;
    }

    public void setEditTag(boolean editTag) {
        this.editTag = editTag;
    }

    private boolean deleteTag;

    @Basic
    @javax.persistence.Column(name = "deleteTag")
    public boolean getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(boolean deleteTag) {
        this.deleteTag = deleteTag;
    }

    private boolean getTag;

    @Basic
    @javax.persistence.Column(name = "getTag")
    public boolean getGetTag() {
        return getTag;
    }

    public void setGetTag(boolean getTag) {
        this.getTag = getTag;
    }

    private boolean getAllTags;

    @Basic
    @javax.persistence.Column(name = "getAllTags")
    public boolean getGetAllTags() {
        return getAllTags;
    }

    public void setGetAllTags(boolean getAllTags) {
        this.getAllTags = getAllTags;
    }

    private boolean getArticlesByTagId;

    @Basic
    @javax.persistence.Column(name = "getArticlesByTagId")
    public boolean getGetArticlesByTagId() {
        return getArticlesByTagId;
    }

    public void setGetArticlesByTagId(boolean getArticlesByTagId) {
        this.getArticlesByTagId = getArticlesByTagId;
    }

    private boolean addUser;

    @Basic
    @javax.persistence.Column(name = "addUser")
    public boolean getAddUser() {
        return addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    private boolean changeUserPrivileges;

    @Basic
    @javax.persistence.Column(name = "changeUserPrivileges")
    public boolean getChangeUserPrivileges() {
        return changeUserPrivileges;
    }

    public void setChangeUserPrivileges(boolean changeUserPrivileges) {
        this.changeUserPrivileges = changeUserPrivileges;
    }

    private boolean deleteUser;

    @Basic
    @javax.persistence.Column(name = "deleteUser")
    public boolean getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    private boolean editUserInfo;

    @Basic
    @javax.persistence.Column(name = "editUserInfo")
    public boolean getEditUserInfo() {
        return editUserInfo;
    }

    public void setEditUserInfo(boolean editUserInfo) {
        this.editUserInfo = editUserInfo;
    }

    private boolean getUser;

    @Basic
    @javax.persistence.Column(name = "getUser")
    public boolean getGetUser() {
        return getUser;
    }

    public void setGetUser(boolean getUser) {
        this.getUser = getUser;
    }

    private boolean getAllUsers;

    @Basic
    @javax.persistence.Column(name = "getAllUsers")
    public boolean getGetAllUsers() {
        return getAllUsers;
    }

    public void setGetAllUsers(boolean getAllUsers) {
        this.getAllUsers = getAllUsers;
    }




    private boolean draftArticle;

    @Basic
    @javax.persistence.Column(name = "draftArticle")
    public boolean getDraftArticle() {
        return draftArticle;
    }

    public void setDraftArticle(boolean draftArticle) {
        this.draftArticle = draftArticle;
    }

    private boolean rejectArticle;

    @Basic
    @javax.persistence.Column(name = "rejectArticle")
    public boolean getRejectArticle() {
        return rejectArticle;
    }

    public void setRejectArticle(boolean rejectArticle) {
        this.rejectArticle = rejectArticle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privileges that = (Privileges) o;

        if (id != that.id) return false;
        if (addArticle != that.addArticle) return false;
        if (editArticle != that.editArticle) return false;
        if (checkArticle != that.checkArticle) return false;
        if (approveArticle != that.approveArticle) return false;
        if (archiveArticle != that.archiveArticle) return false;
        if (featureArticle != that.featureArticle) return false;
        if (deleteArticle != that.deleteArticle) return false;
        if (getArticle != that.getArticle) return false;
        if (getAllArticles != that.getAllArticles) return false;
        if (addTag != that.addTag) return false;
        if (addTagToArticle != that.addTagToArticle) return false;
        if (editTag != that.editTag) return false;
        if (deleteTag != that.deleteTag) return false;
        if (getTag != that.getTag) return false;
        if (getAllTags != that.getAllTags) return false;
        if (getArticlesByTagId != that.getArticlesByTagId) return false;
        if (addUser != that.addUser) return false;
        if (changeUserPrivileges != that.changeUserPrivileges) return false;
        if (deleteUser != that.deleteUser) return false;
        if (editUserInfo != that.editUserInfo) return false;
        if (getUser != that.getUser) return false;
        if (getAllUsers != that.getAllUsers) return false;
        if (draftArticle != that.draftArticle) return false;
        if (rejectArticle != that.rejectArticle) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (addArticle ? 1 : 0);
        result = 31 * result + (editArticle ? 1 : 0);
        result = 31 * result + (checkArticle ? 1 : 0);
        result = 31 * result + (approveArticle ? 1 : 0);
        result = 31 * result + (archiveArticle ? 1 : 0);
        result = 31 * result + (featureArticle ? 1 : 0);
        result = 31 * result + (deleteArticle ? 1 : 0);
        result = 31 * result + (getArticle ? 1 : 0);
        result = 31 * result + (getAllArticles ? 1 : 0);
        result = 31 * result + (addTag ? 1 : 0);
        result = 31 * result + (addTagToArticle ? 1 : 0);
        result = 31 * result + (editTag ? 1 : 0);
        result = 31 * result + (deleteTag ? 1 : 0);
        result = 31 * result + (getTag ? 1 : 0);
        result = 31 * result + (getAllTags ? 1 : 0);
        result = 31 * result + (getArticlesByTagId ? 1 : 0);
        result = 31 * result + (addUser ? 1 : 0);
        result = 31 * result + (changeUserPrivileges ? 1 : 0);
        result = 31 * result + (deleteUser ? 1 : 0);
        result = 31 * result + (editUserInfo ? 1 : 0);
        result = 31 * result + (getUser ? 1 : 0);
        result = 31 * result + (getAllUsers ? 1 : 0);
        result = 31 * result + (draftArticle ? 1 : 0);
        result = 31 * result + (rejectArticle ? 1 : 0);
        return result;
    }
}
