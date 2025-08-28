package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidCountryCodeException;
import com.pix.poc.domain.exception.InvalidLengthException;
import com.pix.poc.domain.exception.MissingPlusException;
import com.pix.poc.domain.exception.NullOrEmptyPhoneNumberException;

import java.util.regex.Pattern;


public record PhoneNumber(String value) {

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");

    public PhoneNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Número de celular não pode ser nulo ou vazio");
        }

        if (!value.startsWith("+")) {
            throw new IllegalArgumentException("Número deve começar com '+'");
        }

        String remaining = value.substring(1);

        if (remaining.length() < 12) {
            throw new IllegalArgumentException("Número muito curto");
        }

        String countryCode = remaining.substring(0, 2);
        if (!NUMERIC_PATTERN.matcher(countryCode).matches()) {
            throw new IllegalArgumentException("Código do país deve ser numérico");
        }


        String ddd = remaining.substring(2, 4);
        if (!NUMERIC_PATTERN.matcher(ddd).matches()) {
            throw new IllegalArgumentException("DDD deve ser numérico");
        }


        String number = remaining.substring(4);
        if (number.length() != 9 || !NUMERIC_PATTERN.matcher(number).matches()) {
            throw new IllegalArgumentException("Número deve ter 9 dígitos numéricos");
        }

    }
}
