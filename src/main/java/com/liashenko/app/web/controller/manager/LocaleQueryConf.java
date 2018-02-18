package com.liashenko.app.web.controller.manager;

import com.liashenko.app.utils.AppProperties;
import com.liashenko.app.web.controller.utils.HttpParser;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LocaleQueryConf {

    private static String SQL_QUERIES_PATH = AppProperties.getSqlQueryFileDir()
            + File.separator + AppProperties.getSqlQueryFileName();

    private static volatile LocaleQueryConf instance;
    private final HashMap<String, ResourceBundle> resourceBundles;

    private LocaleQueryConf() {
        resourceBundles = new HashMap<>();
    }

    public static LocaleQueryConf getInstance() {
        LocaleQueryConf localeInstance = instance;
        if (localeInstance == null) {
            synchronized (LocaleQueryConf.class) {
                localeInstance = instance;
                if (localeInstance == null) {
                    instance = localeInstance = new LocaleQueryConf();
                }
            }
        }
        return instance;
    }

    //returns sql queries for the specified locale
    public ResourceBundle getLocalQueries(String locale) {
        if (resourceBundles.containsKey(locale)) {
            return resourceBundles.get(locale);
        } else {
            resourceBundles.put(locale, ResourceBundle.getBundle(SQL_QUERIES_PATH,
                    HttpParser.parseLocaleFromString(locale)));
            return resourceBundles.get(locale);
        }
    }
}
