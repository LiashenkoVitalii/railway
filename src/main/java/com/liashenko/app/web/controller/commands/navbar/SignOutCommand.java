package com.liashenko.app.web.controller.commands.navbar;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//handles users request to sign out
@Authorization.Allowed(roles = {RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID})
public class SignOutCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(SignOutCommand.class);

    private ServiceFactory serviceFactory;

    public SignOutCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(Boolean.TRUE);
        if (!session.isNew()) {
            session.invalidate();
        } else {
            String userLocale = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            SessionAttrInitializer.newSessionInit(session, userLocale);
            SessionAttrInitializer.newSessionInit(session);
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.INDEX_PAGE_PATH);
    }
}
