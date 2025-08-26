package com.pix.poc.interactors.web.dto.response;

public record DeletionPixResponse(
        String id,
        String pixType,
        Integer agencyNumber,
        Integer accountNumber,
        String name,
        String pixInclusionDate,
        String pixDeactivationDate
) {
}
