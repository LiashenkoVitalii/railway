package com.liashenko.app.service;

import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserDto;

import java.util.List;
import java.util.Optional;

//Interface produces for administrator methods to manage users
public interface AdminService {

    //Returns page with users info (rowsPerPage - count of rows, offset - position to start)
    Optional<List<UserDto>> showUsers(int rowsPerPage, int offset);

    //Returns list of roles
    Optional<List<RoleDto>> showRoles();

    //Updates info about user (userDto should contain userId to update)
    void updateUserInfo(UserDto userDto);

    //Returns count of registered users
    Integer getUsersCount();
}
