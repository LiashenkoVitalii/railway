package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.RouteRateDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.RouteRate;
import com.liashenko.app.persistance.result_parser.ResultSetParser;
import com.liashenko.app.persistance.result_parser.ResultSetParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.liashenko.app.utils.Asserts.*;

public class RouteRateDaoImpl extends AbstractJDBCDao implements RouteRateDao {
    private static final Logger classLogger = LogManager.getLogger(RouteRateDaoImpl.class);

    public RouteRateDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("");
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

    private String getByRouteIdQuery() {
        return localeQueries.getString("select_by_route_id");
    }

    //method used by this method should be implemented
    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<RouteRate> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<RouteRate> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    RouteRate routeRate = ResultSetParser.fillBeanWithResultData(rs, RouteRate.class,
                            localeQueries.getString("locale_suffix"));
                    list.add(routeRate);
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
    public void create(RouteRate object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<RouteRate> persist(RouteRate object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (RouteRate) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(RouteRate object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertIntIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(RouteRate object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertIntIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<RouteRate> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (RouteRate) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<RouteRate>> getAll() {
        return super.getAll().map(list -> (List<RouteRate>) list);
    }

    @Override
    public Optional<RouteRate> getByRouteId(Long routeId) {
        if (assertLongIsNullOrZeroOrLessZero(routeId)) return Optional.empty();
        String sql = getByRouteIdQuery();
        List<RouteRate> routeRateList;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, routeId);
            ResultSet rs = statement.executeQuery();
            routeRateList = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        if (routeRateList == null || routeRateList.size() == 0) {
            return Optional.empty();
        } else if (routeRateList.size() == 1) {
            return Optional.ofNullable(routeRateList.get(0));
        } else {
            classLogger.error("Received more than one record.");
            throw new DAOException("Received more than one record.");
        }
    }
}
