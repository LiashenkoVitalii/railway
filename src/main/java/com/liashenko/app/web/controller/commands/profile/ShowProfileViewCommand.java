package com.liashenko.app.web.controller.commands.profile;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.UserProfileService;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserDto;
import com.liashenko.app.service.exceptions.ServiceException;
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
import java.util.Optional;
import java.util.ResourceBundle;

//returns users profile.jsp page
@Authorization.Allowed(roles = {RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID}, defAction = RequestHelper.INDEX_PAGE_URL_ATTR)
public class ShowProfileViewCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(ShowProfileViewCommand.class);
    private static final String USER_ATTR = "user";

    private ServiceFactory serviceFactory;

    public ShowProfileViewCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(Boolean.TRUE);
        try {
            String localStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(localStr);
            Long userId = HttpParser.getLongSessionAttr(SessionAttrInitializer.USER_ID, session).orElse(0L);
            UserProfileService userProfileService = serviceFactory.getUserProfileService(localeQueries);
            Optional<UserDto> userProfileOpt = userProfileService.getUserById(userId);
            if (userProfileOpt.isPresent()) {
                request.setAttribute(USER_ATTR, userProfileOpt.get());
                page = PageManagerConf.getInstance().getProperty(PageManagerConf.PROFILE_PAGE_PATH);
            } else {
                page = PageManagerConf.getInstance().getProperty(PageManagerConf.LOGIN_PAGE_PATH);
            }
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            page = PageManagerConf.getInstance().getProperty(PageManagerConf.ERROR_PAGE_PATH);
        }
        return page;
    }
}
