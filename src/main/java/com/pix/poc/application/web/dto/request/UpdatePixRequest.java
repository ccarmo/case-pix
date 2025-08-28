package com.pix.poc.application.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePixRequest(
        @JsonProperty("id") String id,
        @JsonProperty("accountNumber") Integer accountNumber,
        @JsonProperty("accountType") String accountType,
        @JsonProperty("agencyNumber") Integer agencyNumber,
        @JsonProperty("nameClient") String nameClient,
        @JsonProperty("lastNameClient") String lastNameClient
) {}
