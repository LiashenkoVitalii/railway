package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.TimeTableDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.TimeTable;
import com.liashenko.app.persistance.result_parser.ResultSetParser;
import com.liashenko.app.persistance.result_parser.ResultSetParserException;
import com.liashenko.app.web.controller.utils.HttpParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static com.liashenko.app.utils.Asserts.assertIsNull;
import static com.liashenko.app.utils.Asserts.assertLongIsNullOrZeroOrLessZero;

public class TimeTableDaoImpl extends AbstractJDBCDao implements TimeTableDao {
    private static final Logger classLogger = LogManager.getLogger(TimeTableDaoImpl.class);

    public TimeTableDaoImpl(Connection connection, ResourceBundle localeQueries) {
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

    private String getTimeTableForDepartureStationByDataAndRouteQuery() {
        return localeQueries.getString("select_timetable_for_station_and_route_and_date");
    }

    //method used by this method should be implemented
    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<TimeTable> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<TimeTable> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    TimeTable timeTable = ResultSetParser.fillBeanWithResultData(rs, TimeTable.class,
                            localeQueries.getString("locale_suffix"));
                    list.add(timeTable);
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
    public void create(TimeTable object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<TimeTable> persist(TimeTable object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (TimeTable) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(TimeTable object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(TimeTable object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<TimeTable> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (TimeTable) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    //method used by this method should be implemented
    @Override
    public Optional<List<TimeTable>> getAll() {
        return super.getAll().map(list -> (List<TimeTable>) list);
    }

    @Override
    public Optional<TimeTable> getTimeTableForStationByDataAndRoute(Long stationId, Long routeId, LocalDate date) {
        if (assertLongIsNullOrZeroOrLessZero(stationId)) return Optional.empty();
        if (assertLongIsNullOrZeroOrLessZero(routeId)) return Optional.empty();
        if (assertIsNull(date)) return Optional.empty();
        String sql = getTimeTableForDepartureStationByDataAndRouteQuery();
        List<TimeTable> timeTableList;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, routeId);
            statement.setLong(2, stationId);
            statement.setString(3, HttpParser.convertDateToDbString(date));
            ResultSet rs = statement.executeQuery();
            timeTableList = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }

        if (timeTableList == null || timeTableList.size() == 0) {
            return Optional.empty();
        } else if (timeTableList.size() == 1) {
            return Optional.ofNullable(timeTableList.get(0));
        } else {
            classLogger.error("Received more than one record.");
            throw new DAOException("Received more than one record.");
        }
    }
}
