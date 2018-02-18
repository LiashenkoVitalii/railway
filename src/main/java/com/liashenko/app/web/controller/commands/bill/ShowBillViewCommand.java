package com.liashenko.app.web.controller.commands.bill;

import com.liashenko.app.service.BillService;
import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.RouteDto;
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
import java.util.ResourceBundle;

//returns page with the bill for ticket to chosen train, route, type of vagon and date
@Authorization.Allowed(roles = RoleDto.USER_ROLE_ID, defAction = RequestHelper.LOGIN_PAGE_URL_ATTR)
@Authorization.Restricted(roles = {RoleDto.ADMIN_ROLE_ID}, action = RequestHelper.ADMIN_WARNING_URL_ATTR)
public class ShowBillViewCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(ShowBillViewCommand.class);

    private static final String FIRST_NAME_ATTR = "firstName";
    private static final String LAST_NAME_ATTR = "lastName";
    private static final String VAGON_TYPE_ID_ATTR = "vagonTypeId";
    private static final String BILL_ATTR = "bill";

    private ServiceFactory serviceFactory;

    public ShowBillViewCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }


    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(Boolean.TRUE);

        try {
            String localStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            RouteDto routeDto = (RouteDto) session.getAttribute(SessionAttrInitializer.USER_ROUTE);
            Long fromStationId = routeDto.getFromStationId();
            Long toStationId = routeDto.getToStationId();
            String date = routeDto.getDateString();

            Long routeId = HttpParser.getLongSessionAttr(SessionAttrInitializer.ROUTE_ID_ATTR, session).orElse(0L);
            Long trainId = HttpParser.getLongSessionAttr(SessionAttrInitializer.TRAIN_ID_ATTR, session).orElse(0L);
            String trainName = HttpParser.getStringSessionAttr(SessionAttrInitializer.TRAIN_NAME_ATTR, session);


            String firstName = HttpParser.getStrAttrFromRequestAndSetToSession(request, FIRST_NAME_ATTR,
                    SessionAttrInitializer.FIRST_NAME_ATTR);
            String lastName = HttpParser.getStrAttrFromRequestAndSetToSession(request, LAST_NAME_ATTR,
                    SessionAttrInitializer.LAST_NAME_ATTR);

            Integer vagonTypeId = HttpParser.getIntAttrFromRequestOrSessionOrDefaultAndSetToSession(request, VAGON_TYPE_ID_ATTR,
                    SessionAttrInitializer.VAGON_TYPE_ID_ATTR, 0);

            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(localStr);
            BillService billService = serviceFactory.getBillService(localeQueries);
            billService.showBill(routeId, trainId, fromStationId, toStationId,
                    trainName, firstName, lastName, vagonTypeId, date)
                    .ifPresent(billDto -> request.setAttribute(BILL_ATTR, billDto));

            page = PageManagerConf.getInstance().getProperty(PageManagerConf.BILL_PAGE_PATH);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            page = PageManagerConf.getInstance().getProperty(PageManagerConf.ERROR_PAGE_PATH);
        }
        return page;
    }
}
