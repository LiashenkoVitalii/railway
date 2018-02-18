package com.liashenko.app.service.data_source;

import java.sql.Connection;

public interface DbConnectionService {

    //Performs rollback for the transaction
    void rollback(Connection connection);

    //Returns connecting to the pool or closes it
    void close(Connection connection);

    //Returns connecting from pool
    Connection getConnection();
}
