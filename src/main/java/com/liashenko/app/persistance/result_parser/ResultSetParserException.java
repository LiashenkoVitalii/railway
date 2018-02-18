package com.liashenko.app.persistance.result_parser;

/*
 * Class designed to wrap possible exceptions from ResultSetParser to unchecked exception
 */
public class ResultSetParserException extends RuntimeException {
    public ResultSetParserException() {
    }

    public ResultSetParserException(String s) {
        super(s);
    }

    public ResultSetParserException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
