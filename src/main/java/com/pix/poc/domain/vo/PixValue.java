package com.pix.poc.domain.vo;

import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.exception.InvalidPixTypeException;

public class PixValue {

    private final String value;
    private final PixType type;

    public PixValue(String value, PixType type) {

        switch (type) {
            case CELULAR -> new PhoneNumber(value);
            case CPF -> new Document(value);
            case CNPJ -> new Document(value);
            case EMAIL -> new Email(value);
            case ALEATORIO -> new PixRandom(value);
            default -> throw new InvalidPixTypeException("Tipo de Pix inválido: " + type);
        }

        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public PixType getType() {
        return type;
    }

}