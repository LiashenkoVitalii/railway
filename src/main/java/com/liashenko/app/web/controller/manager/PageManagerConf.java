package com.liashenko.app.web.controller.manager;

import java.util.ResourceBundle;


//Class retrieves info from file request_mappin.properties
public class PageManagerConf {
    public static final String BILL_PAGE_PATH = "BILL_PAGE_PATH";
    public static final String ORDERS_PAGE_PATH = "ORDERS_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String ORDER_TICKET_PAGE_PATH = "ORDER_TICKET_PAGE_PATH";
    public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";
    public static final String PROFILE_PAGE_PATH = "PROFILE_PAGE_PATH";
    public static final String USERS_PAGE_PATH = "USERS_PAGE_PATH";
    public static final String INDEX_PAGE_PATH = "INDEX_PAGE_PATH";
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String EMPTY_RESULT = "EMPTY_RESULT";
    public static final String ADMIN_WARNING_PAGE_PATH = "ADMIN_WARNING_PAGE_PATH";

    private static final String BUNDLE_NAME = "request_mapping";
    private static volatile PageManagerConf instance;
    private ResourceBundle resourceBundle;

    private PageManagerConf() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static PageManagerConf getInstance() {
        PageManagerConf localInstance = instance;

        if (localInstance == null) {
            synchronized (PageManagerConf.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PageManagerConf();
                }
            }
        }
        return instance;
    }

    //returns paths for the relevant views
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}