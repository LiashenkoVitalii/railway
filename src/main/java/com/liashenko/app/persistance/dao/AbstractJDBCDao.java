package com.liashenko.app.persistance.dao;


import com.liashenko.app.persistance.dao.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Number> implements GenericJDBCDao<T, PK> {

    private static final Logger classLogger = LogManager.getLogger(AbstractJDBCDao.class);

    protected Connection connection;
    protected ResourceBundle localeQueries;

    public AbstractJDBCDao(Connection connection, ResourceBundle localeQueries) {
        this.connection = connection;
        this.localeQueries = localeQueries;
    }

//    public abstract String getExistsQuery();
//
//    public abstract String getSelectQuery();
//
//    public abstract String getCreateQuery();
//
//    public abstract String getUpdateQuery();
//
//    public abstract String getDeleteQuery();

    //Returns parsed result of select query
    protected abstract List<T> parseResultSet(ResultSet rs);

    //Sets args for insert query according to Object-fields
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object);

    //Sets args for update query according to Object-fields
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object);

    public boolean isExists(PK key) {
        int i = 0;
        String sql = getExistsQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();
            if (rs == null) return false;
            while (rs.next()) {
                i++;
            }
        } catch (SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        switch (i) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                classLogger.error("Received more than one record.");
                throw new DAOException("Received more than one record.");
        }
    }


    @Override
    public void create(T object) {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new DAOException("The record isn't created.");
            }
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<T> persist(T object) {
        // Добавляем запись
        Object insertedId;
        try {
            String sql = getCreateQuery();
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                prepareStatementForInsert(statement, object);
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new DAOException("There was an error on persist, rows affected: " + count);
                }
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        insertedId = generatedKeys.getObject(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } catch (DAOException | SQLException e) {
                throw new DAOException(e);
            }
            //Retrieve created row from table
            sql = getSelectQuery() + " WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, insertedId);
                ResultSet rs = statement.executeQuery();
                List<T> list = parseResultSet(rs);
                if ((list == null) || (list.size() != 1)) {
                    throw new DAOException("Exception on findByPK new persist data.");
                }
                return Optional.of(list.get(0));
            } catch (DAOException | SQLException e) {
                throw new DAOException(e.getMessage());
            }
        } catch (DAOException ex) {
            classLogger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> getByPK(PK key) {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        if (list == null || list.size() == 0) {
            classLogger.error("Record with PK = " + key + " not found.");
        } else if (list.size() > 1) {
            classLogger.error("Received more than one record.");
        } else {
            return Optional.ofNullable(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void update(T object) {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On update modify more then 1 record: " + count);
            }
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(T object) {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, object.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On delete modify more then 1 record: " + count);
            }
        } catch (DAOException | SQLException e) {
            classLogger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<List<T>> getAll() {
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            return Optional.ofNullable(parseResultSet(rs));
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
    }
}
