package com.pix.poc.domain.exception;

public class NullOrEmptyPhoneNumberException extends ValidationException {
    public NullOrEmptyPhoneNumberException() {
        super("Número de telefone não pode ser nulo ou vazio.");
    }
}
