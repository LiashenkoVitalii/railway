package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.PricePerKmForVagonDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.PricePerKmForVagon;
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

public class PricePerKmForVagonDaoImpl extends AbstractJDBCDao implements PricePerKmForVagonDao {
    private static final Logger classLogger = LogManager.getLogger(PricePerKmForVagonDaoImpl.class);

    public PricePerKmForVagonDaoImpl(Connection connection, ResourceBundle localeQueries) {
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

    //method used by this method should be implemented
    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    private String getPricePerKmForVagonQuery() {
        return localeQueries.getString("select_price_per_km_for_vagon_type");
    }

    @Override
    protected List<PricePerKmForVagon> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<PricePerKmForVagon> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    PricePerKmForVagon pricePerKmForVagon = ResultSetParser.fillBeanWithResultData(rs,
                            PricePerKmForVagon.class, localeQueries.getString("locale_suffix"));
                    list.add(pricePerKmForVagon);
                } catch (ResultSetParserException ex) {
                    classLogger.error(ex);
                }
            }
        } catch (DAOException | SQLException e) {
            classLogger.error("Couldn't parse ResultSet", e);
            throw new DAOException(e);
        }
        return list;
    }

    //method used by this method should be implemented
    @Override
    public void create(PricePerKmForVagon object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<PricePerKmForVagon> persist(PricePerKmForVagon object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (PricePerKmForVagon) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(PricePerKmForVagon object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(PricePerKmForVagon object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<PricePerKmForVagon> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (PricePerKmForVagon) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    //method used by this method should be implemented
    @Override
    public Optional<List<PricePerKmForVagon>> getAll() {
        return super.getAll().map(list -> (List<PricePerKmForVagon>) list);
    }

    @Override
    public Optional<PricePerKmForVagon> getPricePerKmForVagon(Integer vagonTypeId) {
        if (assertIntIsNullOrZeroOrLessZero(vagonTypeId)) return Optional.empty();
        String sql = getPricePerKmForVagonQuery();
        List<PricePerKmForVagon> priceList;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vagonTypeId);
            ResultSet rs = statement.executeQuery();
            priceList = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        if (priceList == null || priceList.size() == 0) {
            return Optional.empty();
        } else if (priceList.size() == 1) {
            return Optional.ofNullable(priceList.get(0));
        } else {
            classLogger.error("Received more than one record.");
            throw new DAOException("Received more than one record.");
        }
    }
}
