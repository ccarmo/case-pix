package com.pix.poc.application.web.dto.response;

public record SavePixResponse(String id) {
    public static SavePixResponse from(String uuid) {
        return new SavePixResponse(uuid);
    }
}
