package com.pix.poc.application.web.dto.response;

import com.pix.poc.domain.entities.Account;

public record UpdatePixResponse(
        String id,
        String accountType,
        Integer agencyNumber,
        Integer accountNumber,
        String name,
        String lastName
) {

    public static UpdatePixResponse fromUpdatePixRequest(String id, Account account) {
        return new UpdatePixResponse(
            id,
            account.getAccountType().name(),
            account.getAgencyNumber().getValue(),
            account.getAccountNumber().getValue(),
            account.getName(),
            account.getLastName()
        );
    }

}
