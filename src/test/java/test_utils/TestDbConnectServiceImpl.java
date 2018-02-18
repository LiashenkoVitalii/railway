package test_utils;

import com.liashenko.app.service.data_source.DbConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDbConnectServiceImpl implements DbConnectionService {

    private static final String DB_NAME = "railway_test";
    private static final String DB_NETWORK_ADDRESS = "localhost";
    private static final String DB_URL = "jdbc:mysql://" + DB_NETWORK_ADDRESS + "/" + DB_NAME +
            "?characterEncoding=UTF-8";

    //  Test database credentials
    private static final String USER = "root";
    private static final String PASS = "1";

    @Override
    public void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
