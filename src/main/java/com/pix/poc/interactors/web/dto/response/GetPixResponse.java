package com.pix.poc.interactors.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pix.poc.domain.entities.Pix;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record GetPixResponse(
        String id,
        String pixType,
        Integer agencyNumber,
        Integer accountNumber,
        String name,
        String pixInclusionDate,
        String pixDeactivationDate
) {
    public static GetPixResponse fromPix(Pix pix) {

        return new GetPixResponse(
                pix.getUniqueID() != null ? pix.getUniqueID().value() : "",
                pix.getPixType().name() != null ? pix.getPixType().name() : "",
                pix.getAccount().getAgencyNumber().getValue() ,
                pix.getAccount().getAccountNumber().getValue(),
                pix.getAccount().getName() != null ? pix.getAccount().getName() : "",
                pix.getInclusionDate() != null ? pix.getInclusionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                pix.getInactivationDate() != null ? pix.getInactivationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
        );
    }

    public static List<GetPixResponse> fromPixList(List<Pix> pixList) {
        if (pixList == null || pixList.isEmpty()) {
            return List.of();
        }

        return pixList.stream()
                .map(GetPixResponse::fromPix)
                .collect(Collectors.toList());
    }
}

