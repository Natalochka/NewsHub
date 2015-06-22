package com.newshub.core.utils;

/**
 * Created by Natalie_2 on 5/19/2015.
 */
public enum UsersEnum {
    ADMIN("admin"),
    EDITOR("editor"),
    CORRECTOR("corrector"),
    AUTHOR("author");
    String name;

    UsersEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
