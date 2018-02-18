package com.liashenko.app.web.controller.commands.registration;

import com.google.gson.Gson;
import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.UserProfileService;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserDto;
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
import java.io.IOException;
import java.util.ResourceBundle;

//Used register new user
//returns ajax-response with results of handling request
@Authorization.Allowed(roles = RoleDto.GUEST_ROLE_ID)
public class RegisterNewUserCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(RegisterNewUserCommand.class);
    private static final Gson GSON = new Gson();

    private ServiceFactory serviceFactory;

    public RegisterNewUserCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, request.getSession());
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            UserProfileService userProfileService = serviceFactory.getUserProfileService(localeQueries);
            String jsonData = HttpParser.getJsonDataFromRequest(request);
            UserDto userDto = getValidUserDtoFromRequest(jsonData, currentLocaleStr, userProfileService);
            userProfileService.createProfile(userDto);
            MsgSender.sendJsonMsg(response, "", Boolean.TRUE);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            MsgSender.sendJsonMsg(response, "", Boolean.FALSE);
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
    }

    private UserDto getValidUserDtoFromRequest(String jsonData, String currentLocaleStr,
                                               UserProfileService userProfileService) {
        try {
            UserDto userDto = GSON.fromJson(jsonData, UserDto.class);
            if (userProfileService.isEmailExists(userDto.getEmail())) {
                throw new ValidationException("Password already exists");
            }
            return UserDto.builder()
                    .firstName(Validator.validateFirstName(userDto.getFirstName()))
                    .lastName(Validator.validateLastName(userDto.getLastName()))
                    .email(Validator.validateEmail(userDto.getEmail()))
                    .password(Validator.validatePassword(userDto.getPassword()))
                    .roleId(RoleDto.USER_ROLE_ID)
                    .isBanned(Boolean.FALSE)
                    .language(currentLocaleStr)
                    .build();
        } catch (ClassCastException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}
