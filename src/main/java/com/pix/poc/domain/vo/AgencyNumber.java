package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidLengthAccountNumberException;

public class AgencyNumber {
    private String value;

    public AgencyNumber(String value){
        if (value == null || value.isBlank()) {
            throw new InvalidLengthAccountNumberException("Numero de agencia invalido. Máximo de 4 caracteres");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
