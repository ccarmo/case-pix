package com.pix.poc.domain.exception;

public class InvalidPixIdException extends ValidationException {
    public InvalidPixIdException(String message) {
        super(message);
    }
}
