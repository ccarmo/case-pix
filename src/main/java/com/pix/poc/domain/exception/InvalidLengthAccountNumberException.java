package com.pix.poc.domain.exception;

public class InvalidLengthAccountNumberException extends ValidationException {
    public InvalidLengthAccountNumberException(String message) {
        super(message);
    }
}
