package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.RouteDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Route;
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

public class RouteDaoImpl extends AbstractJDBCDao implements RouteDao {
    private static final Logger classLogger = LogManager.getLogger(RouteDaoImpl.class);

    public RouteDaoImpl(Connection connection, ResourceBundle localeQueries) {
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

    private String getFirstTerminalStationOnRouteQuery() {
        return localeQueries.getString("select_first_terminal_station_on_route");
    }

    private String getLastTerminalStationOnRouteQuery() {
        return localeQueries.getString("select_last_terminal_station_on_route");
    }


    private String getRoutesByDepartureAndArrivalQuery() {
        return localeQueries.getString("select_routes_by_departure_and_arrival_stations_from_route_tbl");
    }

    private String getStationOnRouteQuery() {
        return localeQueries.getString("select_station_on_route");
    }

    //method used by this method should be implemented
    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<Route> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<Route> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    Route route = ResultSetParser.fillBeanWithResultData(rs, Route.class, localeQueries.getString("locale_suffix"));
                    list.add(route);
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
    public void create(Route object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<Route> persist(Route object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (Route) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(Route object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(Route object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<Route> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (Route) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<Route>> getAll() {
        return super.getAll().map(list -> (List<Route>) list);
    }

    @Override
    public Optional<List<Route>> getRoutesByDepartureAndArrivalStationsId(Long departureStationId, Long arrivalStationId) {
        if (assertLongIsNullOrZeroOrLessZero(departureStationId)) return Optional.empty();
        if (assertLongIsNullOrZeroOrLessZero(arrivalStationId)) return Optional.empty();
        String sql = getRoutesByDepartureAndArrivalQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, departureStationId);
            statement.setLong(2, arrivalStationId);
            ResultSet rs = statement.executeQuery();
            return Optional.ofNullable(parseResultSet(rs));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Optional<Route> getStationOnRoute(Long stationId, Long routeId) {
        if (assertLongIsNullOrZeroOrLessZero(stationId)) return Optional.empty();
        if (assertLongIsNullOrZeroOrLessZero(routeId)) return Optional.empty();
        List<Route> routeList;
        String sql = getStationOnRouteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, stationId);
            statement.setLong(2, routeId);
            ResultSet rs = statement.executeQuery();
            routeList = parseResultSet(rs);

            if (routeList == null || routeList.size() == 0) {
                return Optional.empty();
            } else if (routeList.size() == 1) {
                return Optional.ofNullable(routeList.get(0));
            } else {
                throw new DAOException("Received more than one record.");
            }
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }


    }

    private Optional<Route> getStationFromRoute(Connection conn, String sql, Long routeId) {
        if (assertLongIsNullOrZeroOrLessZero(routeId)) return Optional.empty();
        List<Route> routeList;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, routeId);
            ResultSet rs = statement.executeQuery();
            routeList = parseResultSet(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (routeList == null || routeList.size() == 0) {
            return Optional.empty();
        } else if (routeList.size() == 1) {
            return Optional.ofNullable(routeList.get(0));
        } else {
            throw new DAOException("Received more than one record.");
        }
    }

    @Override
    public Optional<Route> getFirstTerminalStationOnRoute(Long routeId) {
        String sql = getFirstTerminalStationOnRouteQuery();
        return getStationFromRoute(connection, sql, routeId);
    }

    @Override
    public Optional<Route> getLastTerminalStationOnRoute(Long routeId) {
        String sql = getLastTerminalStationOnRouteQuery();
        return getStationFromRoute(connection, sql, routeId);
    }
}
