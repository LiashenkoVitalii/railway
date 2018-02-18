package com.liashenko.app.service.utils;

import com.liashenko.app.service.exceptions.ServiceException;

public class PasswordProcessorException extends ServiceException {
    public PasswordProcessorException() {
    }

    public PasswordProcessorException(String s) {
        super(s);
    }
}
