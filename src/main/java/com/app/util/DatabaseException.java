package com.app.util;

/**
 * Custom Database Exception
 */
public class DatabaseException extends RuntimeException {
    /**
     * Deafult Constructor
     */
    public DatabaseException() {
        super();
    }

    /**
     * Non-default constructor
     * @param e Wrapped exception
     * @param message Custom exception message
     */
    public DatabaseException(Throwable e, String message) {
        super(message, e);
    }
}
