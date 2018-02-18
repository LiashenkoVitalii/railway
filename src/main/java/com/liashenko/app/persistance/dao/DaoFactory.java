package com.liashenko.app.persistance.dao;


import java.sql.Connection;
import java.util.ResourceBundle;

//Interface contains methods to return implementation of specified DAO
public interface DaoFactory {

    UserDao getUserDao(Connection connection, ResourceBundle localeQueries);

    PasswordDao getPasswordDao(Connection connection, ResourceBundle localeQueries);

    RoleDao getRoleDao(Connection connection, ResourceBundle localeQueries);

    StationDao getStationDao(Connection connection, ResourceBundle localeQueries);

    TrainDao getTrainDao(Connection connection, ResourceBundle localeQueries);

    TimeTableDao getTimeTableDao(Connection connection, ResourceBundle localeQueries);

    RouteDao getRouteDao(Connection connection, ResourceBundle localeQueries);

    VagonTypeDao getVagonTypeDao(Connection connection, ResourceBundle localeQueries);

    RouteRateDao getRouteRateDao(Connection connection, ResourceBundle localeQueries);

    PricePerKmForVagonDao getPricePerKmForVagonDao(Connection connection, ResourceBundle localeQueries);

}
