package com.pix.poc.domain.vo;


import com.pix.poc.domain.exception.InvalidEmailException;
import com.pix.poc.domain.exception.InvalidNameException;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new InvalidNameException("Nome não pode ser nulo ou vazio");
        }
        if (value.length() > 30) {
            throw new InvalidNameException("Nome não pode ter mais que 30 caracteres");
        }
    }
}
