package com.liashenko.app.service;

import com.liashenko.app.service.dto.PrinciplesDto;
import com.liashenko.app.service.dto.UserSessionProfileDto;

import java.util.Optional;

//Contains methods to perform actions connected with authorization
public interface AuthorizationService {

    // Checks user's credentials. If result is successful, returns user's info to store in the http-session,
    // otherwise returns empty Optional
    Optional<UserSessionProfileDto> logIn(PrinciplesDto principlesDto);
}
