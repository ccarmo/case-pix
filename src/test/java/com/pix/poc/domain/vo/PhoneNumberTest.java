package com.pix.poc.domain.vo;

import com.pix.poc.domain.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void shouldCreateValidBrazilianPhoneNumber() {
        String value = "+5511987654321"; // +55 , DDD 11, número 987654321
        PhoneNumber phone = new PhoneNumber(value);
        assertEquals(value, phone.value());
    }

    @Test
    void shouldCreateValidInternationalPhoneNumber() {
        String value = "+121987654321"; // +1 , DDD 21, número 987654321
        PhoneNumber phone = new PhoneNumber(value);
        assertEquals(value, phone.value());
    }

    @Test
    void shouldThrowWhenValueIsNullOrEmpty() {
        assertThrows(InvalidPhoneNumberException.class, () -> new PhoneNumber(null));
        assertThrows(InvalidPhoneNumberException.class, () -> new PhoneNumber(""));
        assertThrows(InvalidPhoneNumberException.class, () -> new PhoneNumber("   "));
    }

    @Test
    void shouldThrowWhenMissingPlus() {
        assertThrows(InvalidPhoneNumberException.class, () -> new PhoneNumber("5511987654321"));
    }

    @Test
    void shouldThrowWhenCountryCodeInvalid() {

        assertThrows(InvalidPhoneNumberException.class, () -> new PhoneNumber("+A5511987654321"));
    }

    @Test
    void shouldAcceptCountryCode1To3DigitsAndDDD2Or3Digits() {
        PhoneNumber p1 = new PhoneNumber("+155987654321"); // código do país 1 dígito
        assertEquals("+155987654321", p1.value());

        PhoneNumber p2 = new PhoneNumber("+5511987654321"); // código do país 2 dígitos
        assertEquals("+5511987654321", p2.value());

        PhoneNumber p3 = new PhoneNumber("+12321987654321"); // código do país 3 dígitos, DDD 2 dígitos
        assertEquals("+12321987654321", p3.value());
    }
}