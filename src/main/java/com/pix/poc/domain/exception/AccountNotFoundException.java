package com.pix.poc.domain.exception;

public class AccountNotFoundException extends ValidationException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
