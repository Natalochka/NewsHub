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

    private byte addArticle;

    @Basic
    @javax.persistence.Column(name = "addArticle")
    public byte getAddArticle() {
        return addArticle;
    }

    public void setAddArticle(byte addArticle) {
        this.addArticle = addArticle;
    }

    private byte addArticleToMain;

    @Basic
    @javax.persistence.Column(name = "addArticleToMain")
    public byte getAddArticleToMain() {
        return addArticleToMain;
    }

    public void setAddArticleToMain(byte addArticleToMain) {
        this.addArticleToMain = addArticleToMain;
    }

    private byte removeArticleFromMain;

    @Basic
    @javax.persistence.Column(name = "removeArticleFromMain")
    public byte getRemoveArticleFromMain() {
        return removeArticleFromMain;
    }

    public void setRemoveArticleFromMain(byte removeArticleFromMain) {
        this.removeArticleFromMain = removeArticleFromMain;
    }

    private byte editArticle;

    @Basic
    @javax.persistence.Column(name = "editArticle")
    public byte getEditArticle() {
        return editArticle;
    }

    public void setEditArticle(byte editArticle) {
        this.editArticle = editArticle;
    }

    private byte publishArticle;

    @Basic
    @javax.persistence.Column(name = "publishArticle")
    public byte getPublishArticle() {
        return publishArticle;
    }

    public void setPublishArticle(byte publishArticle) {
        this.publishArticle = publishArticle;
    }

    private byte archiveArticle;

    @Basic
    @javax.persistence.Column(name = "archiveArticle")
    public byte getArchiveArticle() {
        return archiveArticle;
    }

    public void setArchiveArticle(byte archiveArticle) {
        this.archiveArticle = archiveArticle;
    }

    private byte featureArticle;

    @Basic
    @javax.persistence.Column(name = "featureArticle")
    public byte getFeatureArticle() {
        return featureArticle;
    }

    public void setFeatureArticle(byte featureArticle) {
        this.featureArticle = featureArticle;
    }

    private byte deleteArticle;

    @Basic
    @javax.persistence.Column(name = "deleteArticle")
    public byte getDeleteArticle() {
        return deleteArticle;
    }

    public void setDeleteArticle(byte deleteArticle) {
        this.deleteArticle = deleteArticle;
    }

    private byte getArticle;

    @Basic
    @javax.persistence.Column(name = "getArticle")
    public byte getGetArticle() {
        return getArticle;
    }

    public void setGetArticle(byte getArticle) {
        this.getArticle = getArticle;
    }

    private byte addTag;

    @Basic
    @javax.persistence.Column(name = "addTag")
    public byte getAddTag() {
        return addTag;
    }

    public void setAddTag(byte addTag) {
        this.addTag = addTag;
    }

    private byte editTag;

    @Basic
    @javax.persistence.Column(name = "editTag")
    public byte getEditTag() {
        return editTag;
    }

    public void setEditTag(byte editTag) {
        this.editTag = editTag;
    }

    private byte deleteTag;

    @Basic
    @javax.persistence.Column(name = "deleteTag")
    public byte getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(byte deleteTag) {
        this.deleteTag = deleteTag;
    }

    private byte getTag;

    @Basic
    @javax.persistence.Column(name = "getTag")
    public byte getGetTag() {
        return getTag;
    }

    public void setGetTag(byte getTag) {
        this.getTag = getTag;
    }

    private byte addUser;

    @Basic
    @javax.persistence.Column(name = "addUser")
    public byte getAddUser() {
        return addUser;
    }

    public void setAddUser(byte addUser) {
        this.addUser = addUser;
    }

    private byte changeUserPrivileges;

    @Basic
    @javax.persistence.Column(name = "changeUserPrivileges")
    public byte getChangeUserPrivileges() {
        return changeUserPrivileges;
    }

    public void setChangeUserPrivileges(byte changeUserPrivileges) {
        this.changeUserPrivileges = changeUserPrivileges;
    }

    private byte deleteUser;

    @Basic
    @javax.persistence.Column(name = "deleteUser")
    public byte getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(byte deleteUser) {
        this.deleteUser = deleteUser;
    }

    private byte editUserInfo;

    @Basic
    @javax.persistence.Column(name = "editUserInfo")
    public byte getEditUserInfo() {
        return editUserInfo;
    }

    public void setEditUserInfo(byte editUserInfo) {
        this.editUserInfo = editUserInfo;
    }

    private byte getUser;

    @Basic
    @javax.persistence.Column(name = "getUser")
    public byte getGetUser() {
        return getUser;
    }

    public void setGetUser(byte getUser) {
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
        result = 31 * result + (int) addArticle;
        result = 31 * result + (int) addArticleToMain;
        result = 31 * result + (int) removeArticleFromMain;
        result = 31 * result + (int) editArticle;
        result = 31 * result + (int) publishArticle;
        result = 31 * result + (int) archiveArticle;
        result = 31 * result + (int) featureArticle;
        result = 31 * result + (int) deleteArticle;
        result = 31 * result + (int) getArticle;
        result = 31 * result + (int) addTag;
        result = 31 * result + (int) editTag;
        result = 31 * result + (int) deleteTag;
        result = 31 * result + (int) getTag;
        result = 31 * result + (int) addUser;
        result = 31 * result + (int) changeUserPrivileges;
        result = 31 * result + (int) deleteUser;
        result = 31 * result + (int) editUserInfo;
        result = 31 * result + (int) getUser;
        return result;
    }
}
