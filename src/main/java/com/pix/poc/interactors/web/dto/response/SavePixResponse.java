package com.pix.poc.interactors.web.dto.response;

import java.time.LocalDate;

public record SavePixResponse(String id) {
    public static SavePixResponse from(String uuid) {
        return new SavePixResponse(uuid);
    }
}
