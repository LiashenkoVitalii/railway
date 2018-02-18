package com.liashenko.app.web.controller.commands.users;

import com.google.gson.Gson;
import com.liashenko.app.service.AdminService;
import com.liashenko.app.service.ServiceFactory;
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

import static com.liashenko.app.utils.Asserts.assertIsNull;


//returns ajax response
@Authorization.Allowed(roles = {RoleDto.ADMIN_ROLE_ID})
public class UpdateUserByAdminCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(UpdateUserByAdminCommand.class);
    private static final Gson GSON = new Gson();

    private ServiceFactory serviceFactory;

    public UpdateUserByAdminCommand(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(Boolean.TRUE);
        try {
            String currentLocaleStr = HttpParser.getStringSessionAttr(SessionAttrInitializer.USER_LOCALE, session);
            String jsonData = HttpParser.getJsonDataFromRequest(request);
            ResourceBundle localeQueries = LocaleQueryConf.getInstance().getLocalQueries(currentLocaleStr);
            AdminService adminService = serviceFactory.getAdminService(localeQueries);
            adminService.updateUserInfo(getValidUserFromJsonString(jsonData));
            MsgSender.sendJsonMsg(response, "User is updated", true);
        } catch (ControllerException | ServiceException e) {
            classLogger.error(e);
            MsgSender.sendJsonMsg(response, "User is not updated", false);
        }
        return PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
    }

    private UserDto getValidUserFromJsonString(String jsonString) {
        UserDto userDto;
        try {
            userDto = GSON.fromJson(jsonString, UserDto.class);
        } catch (ClassCastException ex) {
            throw new ValidationException(ex.getMessage());
        }
        if (assertIsNull(userDto.getRoleId()) || assertIsNull(userDto.getBanned())) {
            throw new ValidationException("Object is not valid");
        }
        return userDto;
    }
}
