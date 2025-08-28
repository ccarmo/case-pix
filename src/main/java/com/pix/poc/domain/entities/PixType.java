package com.pix.poc.domain.entities;

import com.pix.poc.domain.exception.InvalidPixTypeException;

public enum PixType {
    CELULAR,
    EMAIL,
    CPF,
    CNPJ,
    ALEATORIO;

    public static PixType fromString(String type) {
        if (type == null || type.isBlank()) {
            throw new InvalidPixTypeException("Tipo de pix não pode ser nulo ou vazio");
        }

        try {
            return PixType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPixTypeException("Tipo de pix não reconhecido: " + type);
        }
    }
}
