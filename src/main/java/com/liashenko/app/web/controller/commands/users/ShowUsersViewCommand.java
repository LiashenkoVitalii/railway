package com.liashenko.app.web.controller.commands.users;


import com.liashenko.app.service.AdminService;
import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.utils.AppProperties;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.RequestHelper;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.LocaleQueryConf;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import com.liashenko.app.web.controller.utils.exceptions.ControllerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;


//returns page with registered users list
@Authorization.Allowed(roles = {RoleDto.ADMIN_ROLE_ID}, defAction = RequestHelper.INDEX_PAGE_URL_ATTR)
public class ShowUsersViewCommand implements ICommand {

    private static final Logger classLogger = LogManager.getLogger(ShowUsersViewCommand.class);

    private static final String OFFSET_ATTR = "offset";
    private static final String COUNT_ATTR = "count";
    private static final String USERS_ON_PAGE_ATTR = "usersOnPage";
    private static final String USERS_LIST_ATTR = "users";
    private static final String ROLES_LIST_ATTR = "roles";

    private static final int USERS_ON_PAGE_DEFAULT = AppProperties.getDefaultUsersOnPageCount();

    private ServiceFactory serviceFactory;

    public ShowUsersViewCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(Boolean.TRUE);
        try {
            String localStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            Integer offset = HttpParser.getIntRequestParam(OFFSET_ATTR, request).orElse(0);
            request.setAttribute(OFFSET_ATTR, offset);

            Integer usersOnPage = HttpParser.getIntAttrFromRequestOrSessionOrDefaultAndSetToSession(request, USERS_ON_PAGE_ATTR,
                    SessionAttrInitializer.USERS_ON_PAGE_ATTR, USERS_ON_PAGE_DEFAULT);
            request.setAttribute(USERS_ON_PAGE_ATTR, usersOnPage);

            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(localStr);
            AdminService adminService = serviceFactory.getAdminService(localeQueries);
            adminService.showUsers(usersOnPage, offset).ifPresent(users -> request.setAttribute(USERS_LIST_ATTR, users));
            adminService.showRoles().ifPresent(roles -> request.setAttribute(ROLES_LIST_ATTR, roles));
            request.setAttribute(COUNT_ATTR, adminService.getUsersCount());
            page = PageManagerConf.getInstance().getProperty(PageManagerConf.USERS_PAGE_PATH);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            page = PageManagerConf.getInstance().getProperty(PageManagerConf.ERROR_PAGE_PATH);
        }
        return page;
    }
}
