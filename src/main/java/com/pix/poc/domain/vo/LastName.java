package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidLastNameException;


public record LastName(String value) {


    public LastName {

        if (value == null || value.isBlank()) {
            value = "";
        }
        if (value.length() > 45) {
            throw new InvalidLastNameException("Sobrenome não pode ter mais que 45 caracteres");
        }
    }
}
