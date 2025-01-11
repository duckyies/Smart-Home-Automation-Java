package com.smarthome.misc;

public class EmptyListAccessException extends RuntimeException {
    public EmptyListAccessException(String message) {
        super(message);
    }

    public EmptyListAccessException() {
        super();
    }

    public EmptyListAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
