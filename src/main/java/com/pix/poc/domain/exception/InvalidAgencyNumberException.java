package com.pix.poc.domain.exception;

public class InvalidAgencyNumberException extends ValidationException {
    public InvalidAgencyNumberException(String message) {
        super(message);
    }
}
