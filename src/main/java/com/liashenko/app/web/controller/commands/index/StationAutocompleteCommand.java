package com.liashenko.app.web.controller.commands.index;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.TrainSearchingService;
import com.liashenko.app.service.dto.AutocompleteDto;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.commands.navbar.SignInCommand;
import com.liashenko.app.web.controller.manager.LocaleQueryConf;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.MsgSender;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import com.liashenko.app.web.controller.utils.exceptions.ControllerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

//returns ajax response
@Authorization.Allowed(roles = {RoleDto.GUEST_ROLE_ID, RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID})
public class StationAutocompleteCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(SignInCommand.class);

    private static final String AUTOCOMPLETE_ATTR = "autocomplete";

    private ServiceFactory serviceFactory;

    public StationAutocompleteCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(Boolean.TRUE);
        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            TrainSearchingService trainSearchingService = serviceFactory.getTrainSearchingService(localeQueries);
            String stationLike = HttpParser.getStringRequestParam(AUTOCOMPLETE_ATTR, request);
            List<AutocompleteDto> stations = trainSearchingService.getStationAutocomplete(stationLike).orElse(Collections.emptyList());
            MsgSender.sendJsonMsg(response, stations);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            MsgSender.sendJsonMsg(response, "");
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
    }
}
