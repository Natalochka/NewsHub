package com.newshub.core.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Natalie_2 on 4/27/2015.
 */
@Entity
public class Articles implements HibernateEntity {
    private int id;
    private String title;
    private String content;
    private Timestamp publicationDate;
    private Byte published;

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
    @Column(name = "publication_date")
    public Timestamp getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Basic
    @Column(name = "published")
    public Byte getPublished() {
        return published;
    }

    public void setPublished(Byte published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Articles articles = (Articles) o;

        if (id != articles.id) return false;
        if (title != null ? !title.equals(articles.title) : articles.title != null) return false;
        if (content != null ? !content.equals(articles.content) : articles.content != null) return false;
        if (publicationDate != null ? !publicationDate.equals(articles.publicationDate) : articles.publicationDate != null)
            return false;
        if (published != null ? !published.equals(articles.published) : articles.published != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (published != null ? published.hashCode() : 0);
        return result;
    }
}
