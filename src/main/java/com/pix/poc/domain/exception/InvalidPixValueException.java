package com.pix.poc.domain.exception;

public class InvalidPixValueException extends ValidationException {
    public InvalidPixValueException(String message) {
        super(message);
    }
}
