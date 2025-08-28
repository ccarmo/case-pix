package com.pix.poc.domain.exception;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
