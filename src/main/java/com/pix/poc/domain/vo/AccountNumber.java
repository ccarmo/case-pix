package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidLengthAccountNumberException;


public class AccountNumber {

    private final Integer value;

    public AccountNumber(Integer value) {
        if (value == null) {
            throw new InvalidLengthAccountNumberException("Número de conta inválido. Nao pode ser nulo");
        }

        String valueAsString = String.valueOf(value);
        if (valueAsString.length() > 8) {
            throw new InvalidLengthAccountNumberException("Número de conta inválido. Máximo de 8 caracteres");
        }
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
