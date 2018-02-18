package com.liashenko.app.web.filters;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.UserProfileService;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.service.implementation.ServiceFactoryImpl;
import com.liashenko.app.web.controller.RequestHelper;
import com.liashenko.app.web.controller.manager.LocaleQueryConf;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

@WebFilter(urlPatterns = {
        RequestHelper.USERS_PAGE_URL_ATTR,
        RequestHelper.LOGIN_PAGE_URL_ATTR,
        RequestHelper.BILL_PAGE_URL_ATTR,
        RequestHelper.REGISTRATION_PAGE_URL_ATTR,
        RequestHelper.PROFILE_PAGE_URL_ATTR,
        RequestHelper.ORDER_TICKET_PAGE_URL_ATTR,
        RequestHelper.INDEX_PAGE_URL_ATTR,
        RequestHelper.SEARCH_TRAINS_URL_ATTR,
        RequestHelper.ORDERS_PAGE_URL_ATTR
},
        filterName = "LanguageChangingFilter",
        description = "Filter for all views to memorize last users page before changing it")
public class LanguageChangingFilter implements Filter {
    private static final Logger classLogger = LogManager.getLogger(LanguageChangingFilter.class);
    private static final String LANGUAGE_ATTR = "lang";

    private ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();

    private void localeProcessing(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, HttpSession session)
            throws IOException, ServletException {
        String page = req.getServletPath();
        String lang = HttpParser.getStringRequestParam(LANGUAGE_ATTR, req);

        //if var lang is empty, memorize page and continue request processing,
        //otherwise memorize user current language to the session
        if (!lang.isEmpty()) {
            session.setAttribute(SessionAttrInitializer.USER_LOCALE, lang);
            Long userRoleId = HttpParser.getLongSessionAttr(SessionAttrInitializer.USER_CURRENT_ROLE, session)
                    .orElse(RoleDto.GUEST_ROLE_ID);
            //if user current role is not "GUEST" memorize current user language to his profile in the db
            if (userRoleId != RoleDto.GUEST_ROLE_ID) {
                Long userId = HttpParser.getLongSessionAttr(SessionAttrInitializer.USER_ID, session).orElse(0L);
                try {
                    ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(lang);
                    UserProfileService userProfileService = serviceFactory.getUserProfileService(localeQueries);
                    userProfileService.changeLanguage(userId, lang);
                } catch (ServiceException ex) {
                    classLogger.error(ex);
                }
            }

            //call page response page to request
            req.getServletContext()
                    .getRequestDispatcher(HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LAST_PAGE, session))
                    .forward(req, resp);
        } else {
            session.setAttribute(SessionAttrInitializer.USER_LAST_PAGE, page);
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(Boolean.TRUE);

        if (session.isNew()) {
            chain.doFilter(req, response);
        } else {
            localeProcessing(req, resp, chain, session);
        }
    }
}
