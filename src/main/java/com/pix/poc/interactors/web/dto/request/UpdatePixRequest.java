package com.pix.poc.interactors.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pix.poc.interactors.web.dto.response.UpdatePixResponse;

public record UpdatePixRequest(
        @JsonProperty("id") String id,
        @JsonProperty("accountNumber") Integer accountNumber,
        @JsonProperty("accountType") String accountType,
        @JsonProperty("agencyNumber") Integer agencyNumber,
        @JsonProperty("nameClient") String nameClient,
        @JsonProperty("lastNameClient") String lastNameClient
) {

}
