package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.DaoFactory;
import com.liashenko.app.persistance.dao.PasswordDao;
import com.liashenko.app.persistance.dao.UserDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Password;
import com.liashenko.app.persistance.domain.User;
import com.liashenko.app.service.AuthorizationService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.PrinciplesDto;
import com.liashenko.app.service.dto.UserSessionProfileDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.service.utils.PasswordProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;

public class AuthorizationServiceImpl implements AuthorizationService {
    private static final Logger classLogger = LogManager.getLogger(AuthorizationServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnSrvc;
    private DaoFactory daoFactory;

    public AuthorizationServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnSrvc = dbConnSrvc;
    }

    @Override
    public Optional<UserSessionProfileDto> logIn(PrinciplesDto principlesDto) {
        if (assertIsNull(principlesDto)) throw new ServiceException("principlesDto is null");
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            User user = userDao.getUserByEmail(principlesDto.getEmail()).orElseThrow(() -> new ServiceException("User is null"));
            //if user is banned he can not be authorized in the system
            if (user.getBanned()) return Optional.empty();
            PasswordDao passwordDao = daoFactory.getPasswordDao(conn, localeQueries);
            Password storedPassword = passwordDao.getByPK(user.getPasswordId())
                    .orElseThrow(() -> new ServiceException("Password is null"));
            char[] passwordToCheck = principlesDto.getPassword();
            if (PasswordProcessor.checkPassword(passwordToCheck, storedPassword)) {
                return Optional.of(new UserSessionProfileDto(user.getId(), user.getRoleId(), user.getLanguage()));
            }
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.empty();
    }
}
