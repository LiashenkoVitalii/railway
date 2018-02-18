package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.DaoFactory;
import com.liashenko.app.persistance.dao.RoleDao;
import com.liashenko.app.persistance.dao.UserDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Role;
import com.liashenko.app.persistance.domain.User;
import com.liashenko.app.service.AdminService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserDto;
import com.liashenko.app.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;


public class AdminServiceImpl implements AdminService {
    private static final Logger classLogger = LogManager.getLogger(AdminServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnSrvc;
    private DaoFactory daoFactory;

    public AdminServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnSrvc = dbConnSrvc;
    }

    @Override
    public Optional<List<UserDto>> showUsers(int rowsPerPage, int offset) {
        List<UserDto> userDtoList = new ArrayList<>();
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            Optional<List<User>> userListOpt = userDao.getPages(rowsPerPage, offset);
            userListOpt.ifPresent(users
                    -> users.forEach(user
                    -> userDtoList.add(UserDto.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .roleId(user.getRoleId())
                    .isBanned(user.getBanned())
                    .email(user.getEmail())
                    .build())));
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.of(userDtoList);
    }

    @Override
    public Optional<List<RoleDto>> showRoles() {
        Connection conn = dbConnSrvc.getConnection();
        List<RoleDto> rolesList = new ArrayList<>();
        try {
            RoleDao roleDao = daoFactory.getRoleDao(conn, localeQueries);
            Optional<List<Role>> rolesListOpt = roleDao.getAll();
            if (rolesListOpt.isPresent()) {
                rolesListOpt.get()
                        .forEach(role -> rolesList.add(new RoleDto(role.getId(), role.getName())));
                return Optional.of(rolesList);
            }
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(UserDto userDto) {
        if (assertIsNull(userDto)) throw new ServiceException("userDto is null");
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            User newUser = userDao.getByPK(userDto.getId()).orElseThrow(() -> new ServiceException("User doesn't exist"));
            newUser.setId(userDto.getId());
            newUser.setBanned(userDto.getBanned());
            newUser.setRoleId(userDto.getRoleId());
            userDao.update(newUser);
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    @Override
    public Integer getUsersCount() {
        Connection conn = dbConnSrvc.getConnection();
        try {
            UserDao userDao = daoFactory.getUserDao(conn, localeQueries);
            return userDao.getCount();
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }
}
