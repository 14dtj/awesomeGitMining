package edu.nju.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by tj on 2016/5/14.
 */
public class Statistics {
    public static String[] categories = {"api", "django", "gem", "jquery", "web", "irc", "plugin", "database", "android", "git",
            "emacs", "linux", "json", "toolkit", ".net", "os", "xml", "ios", "mvc", "vim", "apache", "maven", "mysql"};
    public static final String[] userCompany = {"Shopify","Google","Github","Twitter","Microsoft","Mozilla","Xamarin","Heroku",
            "Facebook","Red Hat"};

    public static List<String> tag = Arrays.asList(categories);

}