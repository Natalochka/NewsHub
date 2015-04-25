package com.newshub.core.domain;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by Natalie on 25.04.2015.
 */
@Entity
@Table(name = "ARTICLES", schema = "NEWSHUB", catalog = "")
public class ArticlesEntity {
    private Integer id;
    private String title;
    private String content;
    private Timestamp dateAndTime;
    private Integer frontNumbering;
    private BigInteger featured;
    private BigInteger approved;
    private BigInteger edited;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "DATE_AND_TIME")
    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Basic
    @Column(name = "FRONT_NUMBERING")
    public Integer getFrontNumbering() {
        return frontNumbering;
    }

    public void setFrontNumbering(Integer frontNumbering) {
        this.frontNumbering = frontNumbering;
    }

    @Basic
    @Column(name = "FEATURED")
    public BigInteger getFeatured() {
        return featured;
    }

    public void setFeatured(BigInteger featured) {
        this.featured = featured;
    }

    @Basic
    @Column(name = "APPROVED")
    public BigInteger getApproved() {
        return approved;
    }

    public void setApproved(BigInteger approved) {
        this.approved = approved;
    }

    @Basic
    @Column(name = "EDITED")
    public BigInteger getEdited() {
        return edited;
    }

    public void setEdited(BigInteger edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesEntity that = (ArticlesEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (dateAndTime != null ? !dateAndTime.equals(that.dateAndTime) : that.dateAndTime != null) return false;
        if (frontNumbering != null ? !frontNumbering.equals(that.frontNumbering) : that.frontNumbering != null)
            return false;
        if (featured != null ? !featured.equals(that.featured) : that.featured != null) return false;
        if (approved != null ? !approved.equals(that.approved) : that.approved != null) return false;
        if (edited != null ? !edited.equals(that.edited) : that.edited != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateAndTime != null ? dateAndTime.hashCode() : 0);
        result = 31 * result + (frontNumbering != null ? frontNumbering.hashCode() : 0);
        result = 31 * result + (featured != null ? featured.hashCode() : 0);
        result = 31 * result + (approved != null ? approved.hashCode() : 0);
        result = 31 * result + (edited != null ? edited.hashCode() : 0);
        return result;
    }
}
