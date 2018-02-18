package com.liashenko.app.web.controller.commands.index;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.TrainSearchingService;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.RouteDto;
import com.liashenko.app.service.dto.TrainDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.web.authorization.Authorization;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


//Returns index page with results of searching needed train
@Authorization.Allowed(roles = {RoleDto.GUEST_ROLE_ID, RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID})
public class SearchTrainsCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(SearchTrainsCommand.class);

    private static final String USER_ROUTE = "userRoute";
    private static final String FROM_ID_ATTR = "fromId";
    private static final String TO_ID_ATTR = "toId";
    private static final String FROM_ATTR = "from";
    private static final String TO_ATTR = "to";
    private static final String DATE_ATTR = "date";
    private static final String TRAINS_ATTR = "trains";
    private static final String IS_LIST_EMPTY = "isListEmpty";

    private ServiceFactory serviceFactory;

    public SearchTrainsCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession(Boolean.TRUE);

        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            RouteDto routeDto = RouteDto.builder()
                    .fromStationId(HttpParser.getLongRequestParam(FROM_ID_ATTR, request).orElse(0L))
                    .fromStationName(HttpParser.getStringRequestParam(FROM_ATTR, request))
                    .toStationId(HttpParser.getLongRequestParam(TO_ID_ATTR, request).orElse(0L))
                    .toStationName(HttpParser.getStringRequestParam(TO_ATTR, request))
                    .dateString(HttpParser.getStringRequestParam(DATE_ATTR, request))
                    .build();

            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            TrainSearchingService trainSearchingService = serviceFactory.getTrainSearchingService(localeQueries);
            Optional<List<TrainDto>> trainsOpt = trainSearchingService.getTrainsForTheRouteOnDate(routeDto);

            request.setAttribute(USER_ROUTE, routeDto);
            session.setAttribute(SessionAttrInitializer.USER_ROUTE, routeDto);
            trainsOpt.ifPresent(trainDtos -> request.setAttribute(TRAINS_ATTR, trainDtos));

            if (trainsOpt.isPresent() && !trainsOpt.get().isEmpty()) {
                request.setAttribute(TRAINS_ATTR, trainsOpt.get());
            } else {
                request.setAttribute(TRAINS_ATTR, null);
                request.setAttribute(IS_LIST_EMPTY, Boolean.TRUE);
            }

            page = PageManagerConf.getInstance().getProperty(PageManagerConf.INDEX_PAGE_PATH);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            page = PageManagerConf.getInstance().getProperty(PageManagerConf.ERROR_PAGE_PATH);
        }
        return page;
    }
}
