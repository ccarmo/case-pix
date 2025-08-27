package com.pix.poc.interactors.web.dto.response;

import java.time.LocalDate;

public record SavePixResponse(String id, LocalDate inclusionDate, LocalDate inactivationDate) {
    public static SavePixResponse from(String uuid, LocalDate inclusionDate, LocalDate inactivationDate) {
        return new SavePixResponse(uuid, inclusionDate, inactivationDate);
    }
}
