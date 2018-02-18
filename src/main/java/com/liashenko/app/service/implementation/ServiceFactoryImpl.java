package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.DaoFactory;
import com.liashenko.app.persistance.dao.mysql.MySQLDaoFactory;
import com.liashenko.app.service.*;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.data_source.implementation.TomcatConnPoolSrc;

import java.util.ResourceBundle;

public class ServiceFactoryImpl implements ServiceFactory {

    private volatile static ServiceFactoryImpl instance;
    private DaoFactory daoFactory = MySQLDaoFactory.getInstance();
    private DbConnectionService dbConnSrvc = TomcatConnPoolSrc.getInstance();

    private ServiceFactoryImpl() {
    }

    public static ServiceFactoryImpl getInstance() {
        ServiceFactoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ServiceFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceFactoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public AuthorizationService getAuthorizationService(ResourceBundle resourceBundle) {
        return new AuthorizationServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }

    @Override
    public BillService getBillService(ResourceBundle resourceBundle) {
        return new BillServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }

    @Override
    public AdminService getAdminService(ResourceBundle resourceBundle) {
        return new AdminServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }

    @Override
    public OrderService getOrderService(ResourceBundle resourceBundle) {
        return new OrderServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }

    @Override
    public TrainSearchingService getTrainSearchingService(ResourceBundle resourceBundle) {
        return new TrainSearchingServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }

    @Override
    public UserProfileService getUserProfileService(ResourceBundle resourceBundle) {
        return new UserProfileServiceImpl(resourceBundle, daoFactory, dbConnSrvc);
    }
}
