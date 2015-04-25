package com.newshub.core.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Natalie on 25.04.2015.
 */
@Entity
@Table(name = "COMMENTS", schema = "NEWSHUB", catalog = "")
public class CommentsEntity {
    private Integer id;
    private String content;
    private Timestamp dateAndTime;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (dateAndTime != null ? !dateAndTime.equals(that.dateAndTime) : that.dateAndTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateAndTime != null ? dateAndTime.hashCode() : 0);
        return result;
    }
}
