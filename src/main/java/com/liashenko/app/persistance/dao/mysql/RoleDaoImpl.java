package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.RoleDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Role;
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

public class RoleDaoImpl extends AbstractJDBCDao implements RoleDao {
    private static final Logger classLogger = LogManager.getLogger(RoleDaoImpl.class);

    public RoleDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("is_id_exists_in_role_tbl");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_role_tbl");
    }

    @Override
    public String getCreateQuery() {
        return localeQueries.getString("create_in_role_tbl");
    }

    @Override
    public String getUpdateQuery() {
        return localeQueries.getString("update_in_role_tbl");
    }

    @Override
    public String getDeleteQuery() {
        return localeQueries.getString("delete_from_role_tbl");
    }

    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<Role> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    Role role = ResultSetParser.fillBeanWithResultData(rs, Role.class, localeQueries.getString("locale_suffix"));
                    list.add(role);
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

    @Override
    public void create(Role object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    @Override
    public Optional<Role> persist(Role object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (Role) obj);
    }

    @Override
    public void update(Role object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    @Override
    public void delete(Role object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<Role> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (Role) obj);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
        try {
            Role role = (Role) object;
            statement.setString(1, role.getName());
        } catch (ClassCastException | SQLException e) {
            classLogger.error("Couldn't make PreparedStatement for INSERT", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
    }

    @Override
    public Optional<List<Role>> getAll() {
        return super.getAll().map(list -> (List<Role>) list);
    }
}
