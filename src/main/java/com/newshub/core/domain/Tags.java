package com.newshub.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Natalie_2 on 5/3/2015.
 */
@Entity
public class Tags implements Serializable, HibernateEntity {
    private static final long serialVersionUID = 12132121213L;
    private int id;
    private String name;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, precision = 15, scale = 0)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tags tags = (Tags) o;

        if (id != tags.id) return false;
        if (name != null ? !name.equals(tags.name) : tags.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
