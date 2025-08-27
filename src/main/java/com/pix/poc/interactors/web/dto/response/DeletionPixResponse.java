package com.pix.poc.interactors.web.dto.response;

import com.pix.poc.domain.entities.Pix;


import java.time.LocalDateTime;

public record DeletionPixResponse(
        String id,
        String pixType,
        Integer agencyNumber,
        Integer accountNumber,
        String name,
        LocalDateTime pixInclusionDate,
        LocalDateTime pixDeactivationDate
) {

    public static DeletionPixResponse toDeletionPixResponse(Pix pix) {
       return new DeletionPixResponse(
               pix.getUniqueID(),
               pix.getPixType().name(),
               pix.getAccount().getAgencyNumber().getValue(),
               pix.getAccount().getAccountNumber().getValue(),
               pix.getAccount().getName(),
               pix.getInclusionDate().toLocalDateTime(),
               pix.getInactivationDate().toLocalDateTime()
       );
    }
}
