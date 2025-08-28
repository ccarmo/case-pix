package com.pix.poc.application.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pix.poc.application.web.validator.ValidPixFilter;

import java.time.LocalDate;

@ValidPixFilter
public class PixFilterRequest {
    private String id;
    private String pixType;
    private Integer agencyNumber;
    private Integer accountNumber;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate pixInclusionDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate pixDeactivationDate;



    public PixFilterRequest(String id, String pixType, Integer agencyNumber, Integer accountNumber, String name, LocalDate pixInclusionDate, LocalDate pixDeactivationDate ) {
        this.id = id;
        this.pixType = pixType;
        this.agencyNumber = agencyNumber;
        this.accountNumber = accountNumber;
        this.name = name;
        this.pixInclusionDate = pixInclusionDate;
        this.pixDeactivationDate = pixDeactivationDate;
    }

    public String getName() {
        return name;
    }

    public Integer getAgencyNumber() {
        return agencyNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getPixType() {
        return pixType;
    }

    public String getId() {
        return id;
    }

    public LocalDate getPixInclusionDate() {
        return pixInclusionDate;
    }

    public LocalDate getPixDeactivationDate() {
        return pixDeactivationDate;
    }
}
