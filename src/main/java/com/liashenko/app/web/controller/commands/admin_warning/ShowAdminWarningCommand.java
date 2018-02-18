package com.liashenko.app.web.controller.commands.admin_warning;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.RequestHelper;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Used in case when Admin tried to do actions not allowed for his role
@Authorization.Allowed(roles = {RoleDto.ADMIN_ROLE_ID}, defAction = RequestHelper.INDEX_PAGE_URL_ATTR)
public class ShowAdminWarningCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(ShowAdminWarningCommand.class);

    private ServiceFactory serviceFactory;

    public ShowAdminWarningCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return PageManagerConf.getInstance().getProperty(PageManagerConf.ADMIN_WARNING_PAGE_PATH);
    }
}
