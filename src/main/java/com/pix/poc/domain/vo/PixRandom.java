package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidPixRandomException;

import java.util.UUID;

public record PixRandom(String value) {

    public PixRandom {
        if (value == null || value.isBlank()) {
            throw new InvalidPixRandomException("Pix aleatório não pode ser nulo ou vazio");
        }

        if (value.length() != 36) {
            throw new InvalidPixRandomException("Pix aleatório deve ter exatamente 36 caracteres");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidPixRandomException("Pix aleatório inválido. Deve ser um UUID válido");
        }

    }
}
