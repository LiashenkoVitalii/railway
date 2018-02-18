package com.liashenko.app.web.controller.commands.index;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//shows empty index page
@Authorization.Allowed(roles = {RoleDto.GUEST_ROLE_ID, RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID})
public class ShowIndexViewCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(SearchTrainsCommand.class);

    private ServiceFactory serviceFactory;

    public ShowIndexViewCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return PageManagerConf.getInstance().getProperty(PageManagerConf.INDEX_PAGE_PATH);
    }
}
