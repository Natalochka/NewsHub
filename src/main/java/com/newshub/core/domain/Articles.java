package com.newshub.core.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Entity
public class Articles implements HibernateEntity {
    private int id;
    private String title;
    private String content;
    private Boolean checked;
    private Boolean featured;
    private Boolean approved;
    private Boolean archived;
    private Boolean draft;
    private Boolean reject;
    private Integer numberOnMain;
    private Timestamp publicationDate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "checked")
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean edited) {
        this.checked = checked;
    }

    @Basic
    @Column(name = "featured")
    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    @Basic
    @Column(name = "approved")
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Basic
    @Column(name = "archived")
    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Basic
    @Column(name = "number_on_main")
    public Integer getNumberOnMain() {
        return numberOnMain;
    }

    public void setNumberOnMain(Integer numberOnMain) {
        this.numberOnMain = numberOnMain;
    }

    @Basic
    @Column(name = "publication_date")
    public Timestamp getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Basic
    @Column(name = "draft")
    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    @Basic
    @Column(name = "reject")
    public Boolean getReject() {
        return reject;
    }

    public void setReject(Boolean reject) {
        this.reject = reject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Articles articles = (Articles) o;

        if (id != articles.id) return false;
        if (title != null ? !title.equals(articles.title) : articles.title != null) return false;
        if (content != null ? !content.equals(articles.content) : articles.content != null) return false;
        if (checked != null ? !checked.equals(articles.checked) : articles.checked != null) return false;
        if (featured != null ? !featured.equals(articles.featured) : articles.featured != null) return false;
        if (approved != null ? !approved.equals(articles.approved) : articles.approved != null) return false;
        if (archived != null ? !archived.equals(articles.archived) : articles.archived != null) return false;
        if (draft != null ? !draft.equals(articles.draft) : articles.draft != null) return false;
        if (reject != null ? !reject.equals(articles.reject) : articles.reject != null) return false;
        if (numberOnMain != null ? !numberOnMain.equals(articles.numberOnMain) : articles.numberOnMain != null)
            return false;
        return !(publicationDate != null ? !publicationDate.equals(articles.publicationDate) : articles.publicationDate != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        result = 31 * result + (featured != null ? featured.hashCode() : 0);
        result = 31 * result + (approved != null ? approved.hashCode() : 0);
        result = 31 * result + (archived != null ? archived.hashCode() : 0);
        result = 31 * result + (draft != null ? draft.hashCode() : 0);
        result = 31 * result + (reject != null ? reject.hashCode() : 0);
        result = 31 * result + (numberOnMain != null ? numberOnMain.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        return result;
    }
}
