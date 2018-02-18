package com.liashenko.app.web.controller.utils.exceptions;

//Class disigned to wrap all controller exceptions
public abstract class ControllerException extends RuntimeException {
    public ControllerException() {
    }

    public ControllerException(String s) {
        super(s);
    }
}
