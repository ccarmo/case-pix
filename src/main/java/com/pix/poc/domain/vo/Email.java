package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidEmailException;

public record Email(String value) {

    public Email {
        if (value == null || value.isBlank()) {
            throw new InvalidEmailException("Email não pode ser nulo ou vazio");
        }
        if (value.length() > 77) {
            throw new InvalidEmailException("Email não pode ter mais que 77 caracteres");
        }
        if (!value.contains("@")) {
            throw new InvalidEmailException("Email deve conter '@'");
        }
    }
}
