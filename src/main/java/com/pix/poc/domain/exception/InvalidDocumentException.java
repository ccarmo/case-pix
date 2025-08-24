package com.pix.poc.domain.exception;

public class InvalidDocumentException extends ValidationException {
    public InvalidDocumentException(String message) {
        super(message);
    }
}
