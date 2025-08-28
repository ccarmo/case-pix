package com.pix.poc.domain.vo;

import java.util.UUID;

public record PixId(String value) {

    public PixId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("PixId não pode ser nulo ou vazio");
        }

        if (value.length() != 36) {
            throw new IllegalArgumentException("PixId deve ter exatamente 36 caracteres");
        }

        // Validação de UUID
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("PixId inválido. Deve ser um UUID válido");
        }

    }
}
