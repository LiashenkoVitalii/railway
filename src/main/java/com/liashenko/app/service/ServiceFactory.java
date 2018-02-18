package com.liashenko.app.service;

import java.util.ResourceBundle;

//Interface contains all needed methods to for controller
public interface ServiceFactory {
    //Produces service to perform actions connected with user authorization
    AuthorizationService getAuthorizationService(ResourceBundle resourceBundle);

    //Produces service for rendering bills
    BillService getBillService(ResourceBundle resourceBundle);

    //Produces for administrator service to manage users
    AdminService getAdminService(ResourceBundle resourceBundle);

    //Produces service to process entered by user info about ordering the ticket
    OrderService getOrderService(ResourceBundle resourceBundle);

    //Produces for user service to provide searching trains and stations using entered by user data
    TrainSearchingService getTrainSearchingService(ResourceBundle resourceBundle);

    //Produces service to register user or update his personal info
    UserProfileService getUserProfileService(ResourceBundle resourceBundle);
}
