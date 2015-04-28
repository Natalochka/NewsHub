package com.newshub.core.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Natalie_2 on 4/27/2015.
 */
@Entity
public class Privileges implements HibernateEntity {
    private int id;
    private String name;
    private byte createArticle;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_article")
    public byte getCreateArticle() {
        return createArticle;
    }

    public void setCreateArticle(byte createArticle) {
        this.createArticle = createArticle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privileges that = (Privileges) o;

        if (id != that.id) return false;
        if (createArticle != that.createArticle) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) createArticle;
        return result;
    }
}
