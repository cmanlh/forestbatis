package com.lifeonwalden.forestbatis.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
