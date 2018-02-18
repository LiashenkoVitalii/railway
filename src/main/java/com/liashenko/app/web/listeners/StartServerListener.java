package com.liashenko.app.web.listeners;

import com.liashenko.app.utils.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@WebListener
public class StartServerListener implements ServletContextListener {
    private static final Logger rootLogger = LogManager.getRootLogger();
    private static final Logger classLogger = LogManager.getLogger(StartServerListener.class);

    //This method is invoked when the Web Application has been removed
    //and is no longer able to accept requests
    public void contextDestroyed(ServletContextEvent event) {
        classLogger.info("Server stopped");
    }

    //This method is invoked when the Web Application
    //is ready to service requests
    public void contextInitialized(ServletContextEvent event) {
        classLogger.info("Server started");
        try {
            //Loads AppProperties from the relevant resource .properties profile
            AppProperties.loadFilesProperties(decodePath("app_settings.properties"));
        } catch (RuntimeException e) {
            classLogger.error("file \'site_config.properties\' not found: ", e);
        }
    }

    private String decodePath(String propertyFilePath) {
        URL url = getClass().getClassLoader().getResource(propertyFilePath);
        if (url != null) {
            try {
                return URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                classLogger.error(e.getMessage());
            }
        } else {
            classLogger.error("File " + propertyFilePath + " not found!");
        }
        return "";
    }
}
