package com.liashenko.app.web.controller;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.implementation.ServiceFactoryImpl;
import com.liashenko.app.web.authorization.RightsChecker;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.commands.NoCommand;
import com.liashenko.app.web.controller.commands.admin_warning.ShowAdminWarningCommand;
import com.liashenko.app.web.controller.commands.bill.ShowBillViewCommand;
import com.liashenko.app.web.controller.commands.error.ShowErrorViewCommand;
import com.liashenko.app.web.controller.commands.index.SearchTrainsCommand;
import com.liashenko.app.web.controller.commands.index.ShowIndexViewCommand;
import com.liashenko.app.web.controller.commands.index.StationAutocompleteCommand;
import com.liashenko.app.web.controller.commands.login.ShowLoginViewCommand;
import com.liashenko.app.web.controller.commands.navbar.SignInCommand;
import com.liashenko.app.web.controller.commands.navbar.SignOutCommand;
import com.liashenko.app.web.controller.commands.order_ticket.ShowOrderTicketViewCommand;
import com.liashenko.app.web.controller.commands.order_ticket.ShowOrdersViewCommand;
import com.liashenko.app.web.controller.commands.profile.CheckIfOtherUsersWithEmailExist;
import com.liashenko.app.web.controller.commands.profile.ShowProfileViewCommand;
import com.liashenko.app.web.controller.commands.profile.UpdateProfileCommand;
import com.liashenko.app.web.controller.commands.registration.CheckIfEmailExistsCommand;
import com.liashenko.app.web.controller.commands.registration.RegisterNewUserCommand;
import com.liashenko.app.web.controller.commands.registration.ShowRegistrationViewCommand;
import com.liashenko.app.web.controller.commands.users.ShowUsersViewCommand;
import com.liashenko.app.web.controller.commands.users.UpdateUserByAdminCommand;
import com.liashenko.app.web.controller.utils.HttpParser;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

// Class designed to perform request mapping
// and provide processing every request with relevant command
public class RequestHelper {
    //common commands
    public static final String DEFAULT_ACTION = "/default";
    public static final String ERROR_ACTION = "/error";
    public static final String ADMIN_WARNING_URL_ATTR = "/admin_warning";
    public static final String INDEX_PAGE_URL_ATTR = "/index.jsp";

    //"index" view commands
    public static final String ORDER_TICKET_PAGE_URL_ATTR = "/order_ticket";
    public static final String SEARCH_TRAINS_URL_ATTR = "/search_trains";
    public static final String STATION_AUTOCOMPLETE_AJAX_ATTR = "/ajax/station_advice";

    //"orders" view commands
    public static final String BILL_PAGE_URL_ATTR = "/bill";

    //"profile" views command
    public static final String UPDATE_PROFILE_AJAX_ATTR = "/ajax/update_user_profile";
    public static final String CHECK_IF_USER_WITH_EMAIL_EXISTS_AJAX_ATTR = "/ajax/check_user_email";

    //"users" view command
    public static final String UPDATE_USER_BUTTON_AJAX_ATTR = "/ajax/update_user";

    //"login" view command
    public static final String SIGN_IN_BUTTON_AJAX_ATTR = "/ajax/sign_in";

    //"registration" view command
    public static final String CHECK_IF_EMAIL_IS_EXISTS_AJAX_ATTR = "/ajax/check_email";
    public static final String NEW_USER_PROFILE_BUTTON_AJAX_ATTR = "/ajax/new_user_profile";

    //"bill" view commands

    //navbar commands
    //guest's commands
    public static final String REGISTRATION_PAGE_URL_ATTR = "/registration";
    public static final String LOGIN_PAGE_URL_ATTR = "/login";

    //user's commands
    public static final String ORDERS_PAGE_URL_ATTR = "/orders";

    //admin's commands
    public static final String USERS_PAGE_URL_ATTR = "/users";

    //user's & admin's commands
    public static final String PROFILE_PAGE_URL_ATTR = "/profile";
    public static final String SIGN_OUT_BUTTON_URL_ATTR = "/sign_out";


    private static volatile RequestHelper instance;
    private HashMap<String, ICommand> commands;
    private ServiceFactory serviceFactory = ServiceFactoryImpl.getInstance();

    private RequestHelper() {
        commands = new HashMap<>();
        //fill mapping table with commands
        commands.put(CHECK_IF_EMAIL_IS_EXISTS_AJAX_ATTR, new CheckIfEmailExistsCommand(serviceFactory));
        commands.put(USERS_PAGE_URL_ATTR, new ShowUsersViewCommand(serviceFactory));
        commands.put(LOGIN_PAGE_URL_ATTR, new ShowLoginViewCommand(serviceFactory));
        commands.put(BILL_PAGE_URL_ATTR, new ShowBillViewCommand(serviceFactory));
        commands.put(REGISTRATION_PAGE_URL_ATTR, new ShowRegistrationViewCommand(serviceFactory));
        commands.put(PROFILE_PAGE_URL_ATTR, new ShowProfileViewCommand(serviceFactory));
        commands.put(ORDERS_PAGE_URL_ATTR, new ShowOrdersViewCommand(serviceFactory));//-
        commands.put(ORDER_TICKET_PAGE_URL_ATTR, new ShowOrderTicketViewCommand(serviceFactory));
        commands.put(INDEX_PAGE_URL_ATTR, new ShowIndexViewCommand(serviceFactory));
        commands.put(SIGN_OUT_BUTTON_URL_ATTR, new SignOutCommand(serviceFactory));
        commands.put(NEW_USER_PROFILE_BUTTON_AJAX_ATTR, new RegisterNewUserCommand(serviceFactory));
        commands.put(UPDATE_USER_BUTTON_AJAX_ATTR, new UpdateUserByAdminCommand(serviceFactory));
        commands.put(SIGN_IN_BUTTON_AJAX_ATTR, new SignInCommand(serviceFactory));
        commands.put(STATION_AUTOCOMPLETE_AJAX_ATTR, new StationAutocompleteCommand(serviceFactory));
        commands.put(SEARCH_TRAINS_URL_ATTR, new SearchTrainsCommand(serviceFactory));
        commands.put(UPDATE_PROFILE_AJAX_ATTR, new UpdateProfileCommand(serviceFactory));
        commands.put(CHECK_IF_USER_WITH_EMAIL_EXISTS_AJAX_ATTR, new CheckIfOtherUsersWithEmailExist(serviceFactory));
        commands.put(DEFAULT_ACTION, new NoCommand(serviceFactory));
        commands.put(ERROR_ACTION, new ShowErrorViewCommand(serviceFactory));
        commands.put(ADMIN_WARNING_URL_ATTR, new ShowAdminWarningCommand(serviceFactory));
    }

    public static RequestHelper getInstance() {
        RequestHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (RequestHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RequestHelper();
                }
            }
        }
        return instance;
    }

    public ICommand getCommand(HttpServletRequest request) {
        String action = request.getServletPath();
        Long currentRole = HttpParser.getLongSessionAttr(SessionAttrInitializer.USER_CURRENT_ROLE, request.getSession())
                .orElse(RoleDto.GUEST_ROLE_ID);
        return RightsChecker.checkUserRightsAndGetCommand(action, DEFAULT_ACTION, currentRole, commands);
    }
}
