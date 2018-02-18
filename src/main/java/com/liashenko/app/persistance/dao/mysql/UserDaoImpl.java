package com.liashenko.app.persistance.dao.mysql;

import com.liashenko.app.persistance.dao.AbstractJDBCDao;
import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.dao.UserDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.User;
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

public class UserDaoImpl extends AbstractJDBCDao implements UserDao {

    private static final Logger classLogger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(Connection connection, ResourceBundle localeQueries) {
        super(connection, localeQueries);
    }

    @Override
    public String getExistsQuery() {
        return localeQueries.getString("is_id_exists_in_user_tbl");
    }

    @Override
    public String getSelectQuery() {
        return localeQueries.getString("select_all_from_user_tbl");
    }

    @Override
    public String getCreateQuery() {
        return localeQueries.getString("create_in_user_tbl");
    }

    @Override
    public String getUpdateQuery() {
        return localeQueries.getString("update_in_user_tbl");
    }

    @Override
    public String getDeleteQuery() {
        return localeQueries.getString("delete_from_user_tbl");
    }

    private String getUserIdByEmailQuery() {
        return localeQueries.getString("get_user_id_by_email_field");
    }

    private String getUserByEmailQuery() {
        return localeQueries.getString("get_user_by_email_field");
    }

    private String getUserByIdAndEmailQuery() {
        return localeQueries.getString("get_user_by_email_and_excluded_id");
    }

    private String getUsersCountQuery() {
        return localeQueries.getString("get_users_count");
    }

    private String getUsersPagesQuery() {
        return localeQueries.getString("select_pages_from_users_tbl");
    }


    @Override
    public boolean isExists(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return false;
        return super.isExists(key);
    }

    @Override
//    SELECT id, firstname, lastname, e_mail, password_id, role_id, is_banned
    protected List<User> parseResultSet(ResultSet rs) {
        if (rs == null) return Collections.emptyList();
        List<User> list = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    User user = ResultSetParser.fillBeanWithResultData(rs, User.class,
                            localeQueries.getString("locale_suffix"));
                    list.add(user);
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
    public void create(User object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        super.create(object);
    }

    @Override
    public Optional<User> persist(User object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        return super.persist(object).map(obj -> (User) obj);
    }

    @Override
    public void update(User object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.update(object);
    }

    @Override
    public void delete(User object) {
        if (assertIsNull(object)) throw new DAOException("Entity is null!");
        if (assertLongIsNullOrZeroOrLessZero(object.getId())) throw new DAOException("Entity id is not valid!");
        super.delete(object);
    }

    @Override
    public Optional<User> getByPK(Long key) {
        if (assertLongIsNullOrZeroOrLessZero(key)) return Optional.empty();
        return super.getByPK(key).map(obj -> (User) obj);
    }

    @Override
//    INSERT INTO railway.user (firstname, lastname, e-mail, password_id, role_id, is_banned) VALUES (?, ?, ?, ?, ?, ?)
    protected void prepareStatementForInsert(PreparedStatement statement, Identified object) {
        try {
            User user = (User) object;
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setLong(4, user.getPasswordId());
            statement.setLong(5, user.getRoleId());
            statement.setBoolean(6, user.getBanned());
            statement.setString(7, user.getLanguage());
        } catch (ClassCastException | SQLException e) {
            classLogger.error("Couldn't make PreparedStatement for INSERT", e);
            throw new DAOException(e);
        }
    }

    @Override
    //UPDATE railway.user SET firstname=? lastname=? e_mail=? password_id=? role_id=? is_banned=? WHERE id= ?
    protected void prepareStatementForUpdate(PreparedStatement statement, Identified object) {
        try {
            User user = (User) object;
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setLong(4, user.getPasswordId());
            statement.setLong(5, user.getRoleId());
            statement.setBoolean(6, user.getBanned());
            statement.setString(7, user.getLanguage());
            statement.setLong(8, user.getId());
        } catch (ClassCastException | SQLException e) {
            classLogger.error("Couldn't make PreparedStatement for UPDATE", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<List<User>> getAll() {
        return super.getAll().map(list -> (List<User>) list);
    }

    @Override
//  SELECT id FROM railway.user WHERE e-mail=?
    public boolean isEmailExists(String email) {
        int i = 0;
        String sql = getUserIdByEmailQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs == null) return false;
            while (rs.next() && i < 1) {
                i++;
            }
            return (i > 0);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean isOtherUsersWithEmailExist(Long userId, String email) {
        if (assertLongIsNullOrZeroOrLessZero(userId)) throw new DAOException("Wrong userId");
        if (assertIsNull(email)) return false;
        int i = 0;
        String sql = getUserByIdAndEmailQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            if (rs == null) return false;
            while (rs.next() && i < 1) {
                i++;
            }
            return (i > 0);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (assertIsNull(email)) return Optional.empty();
        String sql = getUserByEmailQuery();
        List<User> userList;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            userList = parseResultSet(rs);
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        if (userList.size() == 0) {
            return Optional.empty();
        } else if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        } else {
            classLogger.error("Received more than one record.");
            throw new DAOException("Received more than one record.");
        }
    }

    @Override
    public Integer getCount() {
        Integer count = 0;
        String sql = getUsersCountQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                rs.next();
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        return count;
    }

    @Override
//    SELECT * FROM railway.user LIMIT ? OFFSET ?
    public Optional<List<User>> getPages(int rowsPerPage, int offset) {
        if (assertIntIsLessThanZero(offset)) throw new DAOException("offset is not valid");
        if (assertIntIsLessThanZero(rowsPerPage)) throw new DAOException("rowsPerPage is not valid");
        Optional<List<User>> listOpt;
        String sql = getUsersPagesQuery();
        if (rowsPerPage == 0) return getAll();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rowsPerPage);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
            listOpt = Optional.ofNullable(parseResultSet(rs));
        } catch (DAOException | SQLException e) {
            classLogger.error(e);
            throw new DAOException(e);
        }
        return listOpt;
    }
}
