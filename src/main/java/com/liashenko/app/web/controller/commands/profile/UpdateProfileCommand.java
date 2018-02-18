package com.liashenko.app.web.controller.commands.profile;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

//The command to update users profile, returns ajax response
@Authorization.Allowed(roles = {RoleDto.USER_ROLE_ID, RoleDto.ADMIN_ROLE_ID})
public class UpdateProfileCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(UpdateProfileCommand.class);
    private static final Gson GSON = new Gson();

    private ServiceFactory serviceFactory;

    public UpdateProfileCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, request.getSession());
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            UserProfileService userProfileService = serviceFactory.getUserProfileService(localeQueries);
            String jsonData = HttpParser.getJsonDataFromRequest(request);
            Long userId = HttpParser.getLongSessionAttr(SessionAttrInitializer.USER_ID, session).orElse(0L);

            UserDto userDto = getValidUserDtoFromRequest(jsonData, userId, userProfileService);
            userProfileService.updateProfile(userDto);
            MsgSender.sendJsonMsg(response, "", true);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            MsgSender.sendJsonMsg(response, "", false);
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
    }

    private UserDto getValidUserDtoFromRequest(String jsonData, Long userId, UserProfileService userProfileService) {
        UserDto userDto = null;
        try {
            userDto = GSON.fromJson(jsonData, UserDto.class);
            if (userProfileService.isOtherUsersWithEmailExist(userId, userDto.getEmail())) {
                throw new ValidationException("User with this password already exists");
            }
            return UserDto.builder()
                    .userId(userId)
                    .firstName(Validator.validateFirstName(userDto.getFirstName()))
                    .lastName(Validator.validateLastName(userDto.getLastName()))
                    .email(Validator.validateEmail(userDto.getEmail()))
                    .build();
        } catch (ClassCastException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}
