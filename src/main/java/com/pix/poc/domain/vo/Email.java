package com.pix.poc.domain.vo;

public record Email(String value) {

    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        if (value.length() > 77) {
            throw new IllegalArgumentException("Email não pode ter mais que 77 caracteres");
        }
        if (!value.contains("@")) {
            throw new IllegalArgumentException("Email deve conter '@'");
        }
    }
}
