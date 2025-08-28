package com.pix.poc.domain.exception;

public class InvalidPhoneNumberException extends ValidationException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
