package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.VagonTypeDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.VagonType;
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

public class VagonTypeDaoImpl extends AbstractJDBCDao implements VagonTypeDao {
    private static final Logger classLogger = LogManager.getLogger(VagonTypeDaoImpl.class);

    public VagonTypeDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_vagon_type_tbl");
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

    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<VagonType> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<VagonType> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    VagonType vagonType = ResultSetParser.fillBeanWithResultData(rs, VagonType.class, localeQueries.getString("locale_suffix"));
                    list.add(vagonType);
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
    public void create(VagonType object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    //method used by this method should be implemented
    @Override
    public Optional<VagonType> persist(VagonType object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (VagonType) obj);
    }

    //method used by this method should be implemented
    @Override
    public void update(VagonType object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertIntIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    //method used by this method should be implemented
    @Override
    public void delete(VagonType object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertIntIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<VagonType> getByPK(Integer key) {
        if (assertIntIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (VagonType) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<VagonType>> getAll() {
        return super.getAll().map(list -> (List<VagonType>) list);
    }
}
