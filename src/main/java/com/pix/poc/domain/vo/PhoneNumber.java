package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidCountryCodeException;
import com.pix.poc.domain.exception.InvalidLengthException;
import com.pix.poc.domain.exception.MissingPlusException;
import com.pix.poc.domain.exception.NullOrEmptyPhoneNumberException;


import java.util.Objects;

public final class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new NullOrEmptyPhoneNumberException();
        }

        if (!value.startsWith("+")) {
            throw new MissingPlusException();
        }

        String digitsOnly = value.substring(1);

        if (digitsOnly.isEmpty() || digitsOnly.charAt(0) == '0') {
            throw new InvalidCountryCodeException();
        }

        if (digitsOnly.length() < 8 || digitsOnly.length() > 15) {
            throw new InvalidLengthException();
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
