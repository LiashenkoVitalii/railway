package com.liashenko.app.service;

import com.liashenko.app.persistance.dao.DaoFactory;
import com.liashenko.app.persistance.dao.mysql.MySQLDaoFactory;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.implementation.*;
import test_utils.TestDbConnectServiceImpl;

import java.util.ResourceBundle;

public class ServiceTestFactoryImpl implements ServiceFactory {
    private volatile static ServiceTestFactoryImpl instance;
    private final DaoFactory daoFactory = MySQLDaoFactory.getInstance();
    private final DbConnectionService dbConnectServiceImpl = new TestDbConnectServiceImpl();

    private ServiceTestFactoryImpl() {
    }

    public static ServiceTestFactoryImpl getInstance() {
        ServiceTestFactoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ServiceFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceTestFactoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public AuthorizationService getAuthorizationService(ResourceBundle resourceBundle) {
        return new AuthorizationServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }

    @Override
    public BillService getBillService(ResourceBundle resourceBundle) {
        return new BillServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }

    @Override
    public AdminService getAdminService(ResourceBundle resourceBundle) {
        return new AdminServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }

    @Override
    public OrderService getOrderService(ResourceBundle resourceBundle) {
        return new OrderServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }

    @Override
    public TrainSearchingService getTrainSearchingService(ResourceBundle resourceBundle) {
        return new TrainSearchingServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }

    @Override
    public UserProfileService getUserProfileService(ResourceBundle resourceBundle) {
        return new UserProfileServiceImpl(resourceBundle, daoFactory, dbConnectServiceImpl);
    }
}
