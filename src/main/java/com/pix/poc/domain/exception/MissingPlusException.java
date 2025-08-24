package com.pix.poc.domain.exception;

public class MissingPlusException extends ValidationException {
    public MissingPlusException() {
        super("Número deve começar com '+'");
    }
}
