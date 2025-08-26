package com.pix.poc.domain.exception;

public class PixNotFoundException extends ValidationException {
    public PixNotFoundException(String message) {
        super(message);
    }
}
