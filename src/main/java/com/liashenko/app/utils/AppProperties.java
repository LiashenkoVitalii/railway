package com.liashenko.app.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AppProperties {
    private static final Logger classLogger = LogManager.getLogger(AppProperties.class);

    private static Properties properties = new Properties();

    public static String getSqlQueryFileName() {
        return properties.getProperty("sql_queries_file_name", "my_sql_queries");
    }

    public static String getSqlQueryFileDir() {
        return properties.getProperty("sql_queries_file_dir", "sql_queries");
    }

    public static String getSaltGenerationAlg() {
        return properties.getProperty("salt_generation_algorithm", "SHA1PRNG");
    }

    public static String getPassGenerationAlg() {
        return properties.getProperty("password_generation_algorithm", "PBKDF2WithHmacSHA1");
    }

    public static int getPassAlgIterationValue() {
        return getIntValue("password_alg_iteration_val", 1000);
    }

    public static int getDefaultUsersOnPageCount() {
        return getIntValue("default_users_on_page_count", 5);
    }

    public static String getDefaultLocaleStr() {
        return properties.getProperty("default_locale", "uk_UA");
    }

    public static String getSessionInterval() {
        return properties.getProperty("session_interval", "20");
    }

    public static void loadFilesProperties(String filePath) {
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(filePath));) {
            properties.load(fis);
        } catch (IOException e) {
            classLogger.error("Couldn't read file 'app_settings.properties'", e);
            throw new RuntimeException("Couldn't read file 'app_settings.properties'");
        }
    }

    public static int getFirstNameMaxLength() {
        return getIntValue("user.first.name.max.length", 255);
    }

    public static int getFirstNameMinLength() {
        return getIntValue("user.first.name.min.length", 1);
    }

    public static int getLastNameMaxLength() {
        return getIntValue("user.last.name.max.length", 255);
    }

    public static int getLastNameMinLength() {
        return getIntValue("user.last.name.min.length", 1);
    }

    public static int getPassMaxLength() {
        return getIntValue("user.pass.max.length", 255);
    }

    public static int getPassMinLength() {
        return getIntValue("user.pass.min.length", 1);
    }

    private static int getIntValue(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException ignore) {
            return defaultValue;
        }
    }

    public static float getDefPriceForVagonKm() {
        return getFloatValue("default_price_for_vagon_km", 1.0F);
    }

    public static float getDefRouteRate() {
        return getFloatValue("default_route_rate", 1.0F);
    }

    public static int getMaxTicketNumber() {
        return getIntValue("max_ticket_number", 100_000);
    }

    private static float getFloatValue(String key, float defaultValue) {
        try {
            return Float.parseFloat(properties.getProperty(key));
        } catch (ClassCastException ignore) {
            return defaultValue;
        }
    }
}
