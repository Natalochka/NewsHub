package com.newshub.core.domain;

import javax.persistence.*;

/**
 * Created by Natalie on 25.04.2015.
 */
@Entity
@Table(name = "TAGS", schema = "NEWSHUB", catalog = "")
public class TagsEntity {
    private Integer id;
    private String tagName;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TAG_NAME")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagsEntity that = (TagsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }
}
