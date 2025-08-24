package com.pix.poc.domain.exception;

public class InvalidLengthException extends ValidationException {
    public InvalidLengthException() {
        super("Número inválido. Deve ter entre 8 e 15 dígitos no total (incluindo código do país)");
    }
}