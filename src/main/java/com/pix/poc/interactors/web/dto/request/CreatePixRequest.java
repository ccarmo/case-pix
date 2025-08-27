package com.pix.poc.interactors.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePixRequest (

        @JsonProperty("documentNumber")
        String documentNumber,

         @JsonProperty("accountNumber")
        Integer accountNumber,

        @JsonProperty("accountType")
        String accountType,

        @JsonProperty("agencyNumber")
        Integer agencyNumber,

        @JsonProperty("nameClient")
        String nameClient,

        @JsonProperty("lastNameClient")
        String lastNameClient,

        @JsonProperty("pixType")
        String pixType,

        @JsonProperty("pixValue")
        String pixValue
) {}
