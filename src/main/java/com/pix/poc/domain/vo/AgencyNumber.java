package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidLengthAccountNumberException;

public class AgencyNumber {
    private Integer value;

    public AgencyNumber(Integer value){
        if (value == null) {
            throw new InvalidLengthAccountNumberException("Numero de agencia invalido. Nao pode ser nulo");
        }
        String valueAsString = String.valueOf(value);
        if (valueAsString.length() > 4) {
            throw new InvalidLengthAccountNumberException("Numero de agencia invalido. Máximo de 4 caracteres");
        }

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
