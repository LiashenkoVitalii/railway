package com.liashenko.app.service.data_source.implementation;

import com.liashenko.app.service.data_source.DbConnException;
import com.liashenko.app.service.data_source.DbConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class TomcatConnPoolSrc implements DbConnectionService {
    private static final Logger classLogger = LogManager.getLogger(TomcatConnPoolSrc.class);
    private static final String JNDI_NODE = "java:/comp/env/jdbc/railway";
    private static volatile TomcatConnPoolSrc instance;
    private static javax.sql.DataSource dataSource;

    private TomcatConnPoolSrc() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (javax.sql.DataSource) ic.lookup(JNDI_NODE);
        } catch (NamingException e) {
            classLogger.error(e);
            throw new DbConnException("Couldn't get connection with database.");
        }
    }

    public static TomcatConnPoolSrc getInstance() {
        TomcatConnPoolSrc localInstance = instance;
        if (localInstance == null) {
            synchronized (TomcatConnPoolSrc.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TomcatConnPoolSrc();
                }
            }
        }
        return instance;
    }

    public void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                classLogger.error(e);
                throw new DbConnException("Couldn't rollback transaction");
            }
        }
    }

    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                classLogger.error(e);
                throw new DbConnException("Couldn't close connection with database");
            }
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            if (dataSource != null) {
                conn = dataSource.getConnection();
                if (conn == null) throw new SQLException("Couldn't get connection with database. Connection is null");
            }
        } catch (SQLException e) {
            classLogger.error(e);
            throw new DbConnException("Couldn't get connection with database");
        }
        return conn;
    }
}
