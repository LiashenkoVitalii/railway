package com.liashenko.app.web.listeners;

import com.liashenko.app.utils.AppProperties;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger classLogger = LogManager.getLogger(SessionListener.class);

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent e) {

        int sessionInterval = 0;
        try {
            sessionInterval = Integer.valueOf(AppProperties.getSessionInterval());
            e.getSession().setMaxInactiveInterval(sessionInterval * 60); //in seconds
        } catch (NumberFormatException ignore) {
        }
        SessionAttrInitializer.newSessionInit(e.getSession());
    }
}
