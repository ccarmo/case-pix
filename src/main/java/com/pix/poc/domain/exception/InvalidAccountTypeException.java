package com.pix.poc.domain.exception;

public class InvalidAccountTypeException extends ValidationException {
    public InvalidAccountTypeException(String message) {
        super("Tipo de conta inválido: " + message);
    }
}
