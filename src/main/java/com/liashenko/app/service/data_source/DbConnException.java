package com.liashenko.app.service.data_source;

import com.liashenko.app.service.exceptions.ServiceException;


public class DbConnException extends ServiceException {
    public DbConnException() {
    }

    public DbConnException(String s) {
        super(s);
    }

    public DbConnException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DbConnException(Throwable throwable) {
        super(throwable);
    }
}
