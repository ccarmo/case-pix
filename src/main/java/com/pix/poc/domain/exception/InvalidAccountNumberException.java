package com.pix.poc.domain.exception;

public class InvalidAccountNumberException extends ValidationException {
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
