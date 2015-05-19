package com.newshub.core.utils;

/**
 * Created by Natalie_2 on 5/19/2015.
 */
public enum UsersEnum {
    ADMIN("Admin"),
    EDITOR("Editor"),
    CORRECTOR("Corrector"),
    AUTHOR("Author");
    String name;
 UsersEnum(String name){
this.name = name;
}
}
