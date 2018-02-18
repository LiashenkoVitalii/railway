package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.PasswordDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Password;
import com.liashenko.app.persistance.result_parser.ResultSetParser;
import com.liashenko.app.persistance.result_parser.ResultSetParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.liashenko.app.utils.Asserts.assertIsNull;
import static com.liashenko.app.utils.Asserts.assertLongIsNullOrZeroOrLessZero;

public class PasswordDaoImpl extends AbstractJDBCDao implements PasswordDao {

    private static final Logger classLogger = LogManager.getLogger(PasswordDaoImpl.class);

    public PasswordDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    public String getExistsQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_password_tbl");
    }

    @Override
    public String getCreateQuery() {
        return localeQueries.getString("create_in_password_tbl");
    }

    @Override
    public String getUpdateQuery() {
        return localeQueries.getString("update_in_password_tbl");
    }

    @Override
    public String getDeleteQuery() {
        return localeQueries.getString("");
    }

    @Override
    protected List<Password> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<Password> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    Password password = ResultSetParser.fillBeanWithResultData(rs, Password.class,
                            localeQueries.getString("locale_suffix"));
                    list.add(password);
                } catch (ResultSetParserException ex) {
                    classLogger.error(ex);
                }
            }
        } catch (SQLException e) {
            classLogger.error("Couldn't parse ResultSet", e);
            throw new DAOException(e);
        }
        return list;
    }

    //sql-query is not implemented for this method
    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    public void create(Password object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    @Override
    public Optional<Password> persist(Password object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (Password) obj);
    }

    @Override
    public void update(Password object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //sql-query is not implemented for this method
    @Override
    public void delete(Password object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<Password> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (Password) obj);
    }

    @Override
//  INSERT INTO railway.password (password, salt, iterations, algorithm) VALUES (?, ?, ?, ?)
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
        try {
            Password password = (Password) object;
            statement.setBytes(1, password.getPassword());
            statement.setBytes(2, password.getSalt());
            statement.setInt(3, password.getIterations());
            statement.setString(4, password.getAlgorithm());
        } catch (ClassCastException | SQLException e) {
            classLogger.error("Couldn't make PreparedStatement for INSERT", e);
            throw new DAOException(e);
        }
    }

    @Override
//  UPDATE railway.password SET password=? salt=? iterations=?, algorithm=? WHERE id= ?
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
        try {
            Password password = (Password) object;
            statement.setBytes(1, password.getPassword());
            statement.setBytes(2, password.getSalt());
            statement.setInt(3, password.getIterations());
            statement.setString(4, password.getAlgorithm());
            statement.setLong(5, password.getId());
        } catch (ClassCastException | SQLException e) {
            classLogger.error("Couldn't make PreparedStatement for UPDATE", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<List<Password>> getAll() {
        return super.getAll().map(list -> (List<Password>) list);
    }
}
