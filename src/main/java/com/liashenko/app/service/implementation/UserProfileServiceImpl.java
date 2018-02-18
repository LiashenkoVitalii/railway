package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.DaoFactory;
import com.liashenko.app.persistance.dao.PasswordDao;
import com.liashenko.app.persistance.dao.UserDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Password;
import com.liashenko.app.persistance.domain.User;
import com.liashenko.app.service.UserProfileService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.UserDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.service.utils.PasswordProcessor;
import com.liashenko.app.utils.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;

public class UserProfileServiceImpl implements UserProfileService {
    private static final Logger classLogger = LogManager.getLogger(UserProfileServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnSrvc;
    private DaoFactory daoFactory;

    public UserProfileServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnSrvc = dbConnSrvc;
    }

    @Override
    public void createProfile(UserDto userDto) {
        if (assertIsNull(userDto)) throw new ServiceException("userDto is null");
        Connection conn = dbConnSrvc.getConnection();
        try {
            //used transaction to keep safe data for different tables
            conn.setAutoCommit(Boolean.FALSE);
            PasswordDao passwordDao = daoFactory.getPasswordDao(conn, localeQueries);
            byte[] salt = PasswordProcessor.generateSalt(userDto.getPassword().length);
            byte[] encryptedPass = PasswordProcessor.getEncryptedPass(userDto.getPassword(), salt);

            Password password = new Password(encryptedPass, salt, AppProperties.getPassAlgIterationValue(),
                    AppProperties.getPassGenerationAlg());
            password = passwordDao.persist(password)
                    .orElseThrow(() -> new ServiceException("Couldn't persist password"));
            User newUser = getUserEntity(userDto, password);
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            userDao.create(newUser);
            conn.commit();
        } catch (ServiceException | SQLException | DAOException e) {
            classLogger.error(e);
            dbConnSrvc.rollback(conn);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    private User getUserEntity(UserDto userDto, Password pass) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setLanguage(userDto.getLanguage());
        user.setBanned(userDto.getBanned());
        user.setRoleId(userDto.getRoleId());
        user.setPasswordId(pass.getId());
        return user;
    }

    @Override
    public void changeLanguage(Long userId, String newLanguage) {
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            User newUser = userDao.getByPK(userId).orElseThrow(() -> new ServiceException("User doesn't exist"));
            newUser.setLanguage(newLanguage);
            userDao.update(newUser);
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            return userDao.isEmailExists(email);
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        Connection conn = dbConnSrvc.getConnection();
        UserDto userDto = null;
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            Optional<User> userOpt = userDao.getByPK(userId);
            if (userOpt.isPresent()) {
                userDto = UserDto.builder()
                        .email(userOpt.get().getEmail())
                        .firstName(userOpt.get().getFirstName())
                        .lastName(userOpt.get().getLastName())
                        .build();
            }
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.ofNullable(userDto);
    }

    @Override
    public void updateProfile(UserDto userDto) {
        if (assertIsNull(userDto)) throw new ServiceException("userDto is null");
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            User user = userDao.getByPK(userDto.getId()).orElseThrow(() -> new ServiceException("User doesn't exist"));
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            userDao.update(user);
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    @Override
    public boolean isOtherUsersWithEmailExist(Long userId, String email) {
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            return userDao.isOtherUsersWithEmailExist(userId, email);
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }
}
