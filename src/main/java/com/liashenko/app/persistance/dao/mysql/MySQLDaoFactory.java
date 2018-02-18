package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ResourceBundle;

//Implementation of DAO-factory for MySQL database
public class MySQLDaoFactory implements DaoFactory {
    private static final Logger classLogger = LogManager.getLogger(MySQLDaoFactory.class);
    private static volatile MySQLDaoFactory instance;

    private MySQLDaoFactory() {
    }

    public static MySQLDaoFactory getInstance() {
        MySQLDaoFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (MySQLDaoFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySQLDaoFactory();
                }
            }
        }
        return instance;
    }

    public UserDao getUserDao(Connection connection, ResourceBundle localeQueries) {
        return new UserDaoImpl(connection, localeQueries);
    }

    public PasswordDao getPasswordDao(Connection connection, ResourceBundle localeQueries) {
        return new PasswordDaoImpl(connection, localeQueries);
    }

    public RoleDao getRoleDao(Connection connection, ResourceBundle localeQueries) {
        return new RoleDaoImpl(connection, localeQueries);
    }

    public StationDao getStationDao(Connection connection, ResourceBundle localeQueries) {
        return new StationDaoImpl(connection, localeQueries);
    }

    public TrainDao getTrainDao(Connection connection, ResourceBundle localeQueries) {
        return new TrainDaoImpl(connection, localeQueries);
    }

    public TimeTableDao getTimeTableDao(Connection connection, ResourceBundle localeQueries) {
        return new TimeTableDaoImpl(connection, localeQueries);
    }

    public RouteDao getRouteDao(Connection connection, ResourceBundle localeQueries) {
        return new RouteDaoImpl(connection, localeQueries);
    }

    public VagonTypeDao getVagonTypeDao(Connection connection, ResourceBundle localeQueries) {
        return new VagonTypeDaoImpl(connection, localeQueries);
    }

    public RouteRateDao getRouteRateDao(Connection connection, ResourceBundle localeQueries) {
        return new RouteRateDaoImpl(connection, localeQueries);
    }

    public PricePerKmForVagonDao getPricePerKmForVagonDao(Connection connection, ResourceBundle localeQueries) {
        return new PricePerKmForVagonDaoImpl(connection, localeQueries);
    }
}
