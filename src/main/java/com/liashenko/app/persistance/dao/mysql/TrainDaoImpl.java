package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.TrainDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Train;
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

public class TrainDaoImpl extends AbstractJDBCDao implements TrainDao {
    private static final Logger classLogger = LogManager.getLogger(TrainDaoImpl.class);

    public TrainDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_train_tbl");
    }

    @Override
    public String getCreateQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getUpdateQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getDeleteQuery() {
        return localeQueries.getString("");
    }

    private String getByRouteQuery() {
        return localeQueries.getString("select_train_by_route");
    }

    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<Train> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<Train> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    Train train = ResultSetParser.fillBeanWithResultData(rs, Train.class, localeQueries.getString("locale_suffix"));
                    list.add(train);
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

    //method used by this method should be implemented
    @Override
    public void create(Train object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<Train> persist(Train object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (Train) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(Train object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(Train object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<Train> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (Train) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<Train>> getAll() {
        return super.getAll().map(list -> (List<Train>) list);
    }

    @Override
    public Optional<Train> getByRoute(Long routeId) {
        if (assertLongIsNullOrZeroOrLessZero(routeId)) return Optional.empty();
        String sql = getByRouteQuery();
        List<Train> userList;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, routeId);
            ResultSet rs = statement.executeQuery();
            userList = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        if (userList == null || userList.size() == 0) {
            return Optional.empty();
        } else if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        } else {
            classLogger.error("Received more than one record.");
            throw new DAOException("Received more than one record.");
        }

    }
}
