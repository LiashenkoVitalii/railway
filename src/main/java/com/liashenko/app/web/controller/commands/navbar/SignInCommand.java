package com.liashenko.app.web.controller.commands.navbar;

import com.google.gson.Gson;
import com.liashenko.app.service.AuthorizationService;
import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.PrinciplesDto;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserSessionProfileDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.web.authorization.Authorization;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.LocaleQueryConf;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.MsgSender;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import com.liashenko.app.web.controller.utils.Validator;
import com.liashenko.app.web.controller.utils.exceptions.ControllerException;
import com.liashenko.app.web.controller.utils.exceptions.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;

//handles users request to sign in
@Authorization.Allowed(roles = RoleDto.GUEST_ROLE_ID)
public class SignInCommand implements ICommand {

    private static final Logger classLogger = LogManager.getLogger(SignInCommand.class);
    private static final Gson GSON = new Gson();

    private ServiceFactory serviceFactory;

    public SignInCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(Boolean.TRUE);

        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            AuthorizationService authorizationService = serviceFactory.getAuthorizationService(localeQueries);
            String jsonData = HttpParser.getJsonDataFromRequest(request);
            Optional<UserSessionProfileDto> userProfileOpt = authorizationService.logIn(getValidPrinciples(jsonData));

            if (userProfileOpt.isPresent()) {
                session.setAttribute(SessionAttrInitializer.USER_ID, userProfileOpt.get().getUserId());
                session.setAttribute(SessionAttrInitializer.USER_CURRENT_ROLE, userProfileOpt.get().getUserRoleId());
                session.setAttribute(SessionAttrInitializer.USER_LOCALE, userProfileOpt.get().getLanguage());
                String pageForRedirecting = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_PAGE_BEFORE_LOGIN, session);
                MsgSender.sendJsonMsg(response, Validator.removeFirstSymbolFromString(pageForRedirecting), true);
            } else {
                MsgSender.sendJsonMsg(response, "", Boolean.TRUE);
            }
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            MsgSender.sendJsonMsg(response, "", Boolean.FALSE);
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
    }

    private PrinciplesDto getValidPrinciples(String jsonString) {
        PrinciplesDto principlesDto;
        try {
            principlesDto = GSON.fromJson(jsonString, PrinciplesDto.class);
        } catch (ClassCastException ex) {
            classLogger.error(ex);
            throw new ValidationException(ex.getMessage());
        }
        if (assertIsNull(principlesDto.getPassword())) {
            throw new ValidationException("Object is not valid");
        }
        return new PrinciplesDto(Validator.validateEmail(principlesDto.getEmail()), principlesDto.getPassword());
    }
}
