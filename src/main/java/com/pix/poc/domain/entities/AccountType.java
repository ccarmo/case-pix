package com.pix.poc.domain.entities;

import com.pix.poc.domain.exception.InvalidAccountTypeException;

public enum AccountType {
    CORRENTE,
    POUPANCA;

    public static AccountType valueOfOrThrow(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new InvalidAccountTypeException(value);
    }
}