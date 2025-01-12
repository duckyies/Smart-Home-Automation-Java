package com.smarthome.misc;

public class RuleParsingException extends RuntimeException {
    public RuleParsingException(String message) {
        super(message);
    }

    public RuleParsingException() {
        super();
    }

    public RuleParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
