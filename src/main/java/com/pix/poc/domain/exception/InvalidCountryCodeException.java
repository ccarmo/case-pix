package com.pix.poc.domain.exception;

public class InvalidCountryCodeException extends ValidationException {
    public InvalidCountryCodeException() {
        super("Código do país inválido. Não pode começar com 0");
    }
}

