package com.newshub.core.utils;

/**
 * Created by Natalie on 24.04.2015.
 */
public class QueryRepository {
    public static String getNumberOnMain = "select max(number_on_main) from articles";
    public static String getLastImageNumber = "select max(image) from articles";
    public static String getArticlesByTitle = "select a.id from Articles a where a.title like ?";
}
