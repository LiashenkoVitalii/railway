package com.liashenko.app.web.controller.commands.order_ticket;

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

//Returns order.jsp view (is under construction as far)
@Authorization.Allowed(roles = {RoleDto.USER_ROLE_ID}, defAction = RequestHelper.LOGIN_PAGE_URL_ATTR)
public class ShowOrdersViewCommand implements ICommand {

    private static final Logger classLogger = LogManager.getLogger(ShowOrdersViewCommand.class);

    private ServiceFactory serviceFactory;

    public ShowOrdersViewCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return PageManagerConf.getInstance().getProperty(PageManagerConf.ORDERS_PAGE_PATH);
    }
}
