package com.liashenko.app.service;

import com.liashenko.app.service.dto.UserDto;

import java.util.Optional;

//Contains methods to register user or update entered info
public interface UserProfileService {

    void createProfile(UserDto userDto);

    //Used by LanguageChangingFilter
    void changeLanguage(Long userId, String newLanguage);

    //Returns "true" if email is already belongs to registered earlier user
    boolean isEmailExists(String email);

    Optional<UserDto> getUserById(Long userId);

    void updateProfile(UserDto userDto);

    //Returns "true" if another user already has this email, otherwise returns "false"
    boolean isOtherUsersWithEmailExist(Long userId, String email);
}
