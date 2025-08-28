package com.pix.poc.domain.vo;


import com.pix.poc.domain.exception.InvalidPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PhoneNumber(String value) {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+(\\d{1,2})(\\d{1,3})(\\d{9})$");

    public PhoneNumber {
        if (value == null || value.isBlank()) {
            throw new InvalidPhoneNumberException("Número de celular não pode ser nulo ou vazio");
        }

        Matcher matcher = PHONE_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException(
                    "Número inválido. Formato esperado: +<códigoPais 1-2 dígitos><DDD 2-3 dígitos><número 9 dígitos>"
            );
        }

    }
}