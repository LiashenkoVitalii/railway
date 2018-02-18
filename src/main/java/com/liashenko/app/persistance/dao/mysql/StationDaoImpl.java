package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.StationDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Station;
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

public class StationDaoImpl extends AbstractJDBCDao implements StationDao {
    private static final Logger classLogger = LogManager.getLogger(StationDaoImpl.class);

    public StationDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_station_tbl");
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


    private String getStationsLikeQuery() {
        return localeQueries.getString("select_from_station_tbl_like");
    }

    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<Station> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<Station> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    Station station = ResultSetParser.fillBeanWithResultData(rs, Station.class,
                            localeQueries.getString("locale_suffix"));
                    list.add(station);
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
    public void create(Station object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<Station> persist(Station object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (Station) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(Station object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(Station object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<Station> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (Station) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<Station>> getAll() {
        return super.getAll().map(list -> (List<Station>) list);
    }

    public Optional<List<Station>> getStationsLike(String likePattern) {
        if (assertIsNull(likePattern)) throw new DAOException("pattern is null");
        Optional<List<Station>> listOpt;
        String sql = getStationsLikeQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, likePattern);
            ResultSet rs = statement.executeQuery();
            listOpt = Optional.ofNullable(parseResultSet(rs));
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        return listOpt;
    }
}
