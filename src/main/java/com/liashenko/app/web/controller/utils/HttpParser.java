package com.liashenko.app.web.controller.utils;

import com.liashenko.app.utils.AppProperties;
import com.liashenko.app.web.controller.utils.exceptions.HttpParserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

public abstract class HttpParser {

    public static Locale parseLocaleFromString(String str) {
        if (str != null && !str.isEmpty()) {
            String[] localStrArr = str.trim().split("_");
            return (localStrArr.length == 2)
                    ? new Locale(localStrArr[0], localStrArr[1]) : new Locale(AppProperties.getDefaultLocaleStr());
        }
        return new Locale(AppProperties.getDefaultLocaleStr());
    }

    public static String getStringRequestParam(String param, HttpServletRequest request) {
        if (param == null) throw new HttpParserException("Wrong request parameter");
        String extractedParam = request.getParameter(param);
        return (extractedParam == null) ? "" : extractedParam.trim();
    }

    public static Optional<Integer> getIntRequestParam(String param, HttpServletRequest request) {
        try {
            return Optional.ofNullable(Integer.valueOf(getStringRequestParam(param, request)));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

    public static LocalDate convertStringToDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return LocalDate.parse(stringDate, formatter);
        } catch (DateTimeParseException ex) {
            throw new HttpParserException(ex.getMessage());
        }
    }

    public static String convertDateToHumanReadableString(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return date.format(formatter);
        } catch (DateTimeParseException ignore) {
            return "";
        }
    }

    public static String convertDateToDbString(LocalDate date) {
        if (date == null) return "";
        StringBuilder timeStr = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return timeStr.append("\"").append(date.format(formatter)).append("\"").toString();
        } catch (DateTimeParseException ignore) {
            return "";
        }
    }

    public static String convertDateTimeToHumanReadableString(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm dd.MM.yyyy");
        try {
            return dateTime.format(formatter);
        } catch (DateTimeParseException ignore) {
            return "";
        }
    }

    public static Optional<Long> getLongRequestParam(String param, HttpServletRequest request) {
        try {
            return Optional.ofNullable(Long.valueOf(getStringRequestParam(param, request)));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

    public static String getStringSessionAttr(String sessionAttribute, HttpSession session) {
        if (sessionAttribute == null || session == null) throw new HttpParserException("Wrong method parameters");
        if (session.getAttribute(sessionAttribute) != null) {
            return session.getAttribute(sessionAttribute).toString().trim();

        }
        return "";
    }

    public static Optional<Long> getLongSessionAttr(String sessionAttribute, HttpSession session) {
        if (sessionAttribute == null || session == null) throw new HttpParserException("Wrong method parameters");
        try {
            if (session.getAttribute(sessionAttribute) != null) {
                return Optional.ofNullable((Long) session.getAttribute(sessionAttribute));
            }
        } catch (NumberFormatException ex) {
            throw new HttpParserException(ex.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<Integer> getIntSessionAttr(String sessionAttribute, HttpSession session) {
        if (sessionAttribute == null || session == null) throw new HttpParserException("Wrong method parameters");
        try {
            if (session.getAttribute(sessionAttribute) != null) {
                return Optional.ofNullable((Integer) session.getAttribute(sessionAttribute));
            }
        } catch (NumberFormatException ex) {
            throw new HttpParserException(ex.getMessage());
        }
        return Optional.empty();
    }

    public static String getJsonDataFromRequest(HttpServletRequest request) throws IOException {
        String jsonData;
        StringBuilder sb = new StringBuilder();
        while ((jsonData = request.getReader().readLine()) != null) {
            sb.append(jsonData);
        }
        return sb.toString();
    }

    private static Integer getIntFromRequestOrFromSessionOrDefault(HttpServletRequest request, String reqAttr,
                                                                   String sessionAttr, Integer defaultVal) {
        return getIntRequestParam(reqAttr, request).orElseGet(()
                -> getIntSessionAttr(sessionAttr, request.getSession()).orElse(defaultVal));
    }

    private static Long getLongFromRequestOrFromSessionOrDefault(HttpServletRequest request, String reqAttr,
                                                                 String sessionAttr, Long defaultVal) {
        return getLongRequestParam(reqAttr, request).orElseGet(()
                -> getLongSessionAttr(sessionAttr, request.getSession()).orElse(defaultVal));
    }

    public static String getStrAttrFromRequestAndSetToSession(HttpServletRequest request, String reqAttr, String sessionAttr) {
        String value = getStringRequestParam(reqAttr, request);
        if (!value.isEmpty()) {
            request.getSession().setAttribute(sessionAttr, value);
        }
        return value;
    }

    public static Long getLongAttrFromRequestOrSessionOrDefaultAndSetToSession(HttpServletRequest request, String reqAttr,
                                                                               String sessionAttr, Long defaultValue) {
        Long value = getLongFromRequestOrFromSessionOrDefault(request, reqAttr, sessionAttr, defaultValue);
        request.getSession().setAttribute(sessionAttr, value);
        return value;
    }

    public static Integer getIntAttrFromRequestOrSessionOrDefaultAndSetToSession(HttpServletRequest request, String reqAttr,
                                                                                 String sessionAttr, Integer defaultValue) {
        Integer value = getIntFromRequestOrFromSessionOrDefault(request, reqAttr, sessionAttr, defaultValue);
        request.getSession().setAttribute(sessionAttr, value);
        return value;
    }
}
