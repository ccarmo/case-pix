package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidLengthAccountNumberException;


public class AccountNumber {

    private final String value;

    public AccountNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidLengthAccountNumberException("Numero de conta invalido. Máximo de 8 caracteres");
        }

        this.value = value;
    }


}
