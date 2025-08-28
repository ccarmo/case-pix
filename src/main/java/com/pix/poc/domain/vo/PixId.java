package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidPixIdException;

import java.util.UUID;

public record PixId(String value) {

    public PixId {
        if (value == null || value.isBlank()) {
            throw new InvalidPixIdException("PixId não pode ser nulo ou vazio");
        }

        if (value.length() != 36) {
            throw new InvalidPixIdException("PixId deve ter exatamente 36 caracteres");
        }

        // Validação de UUID
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidPixIdException("PixId inválido. Deve ser um UUID válido");
        }

    }
}
