package com.pix.poc.interactors.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.domain.vo.PixValue;

public class CreatePixRequest {

    @JsonProperty("documentNumber")
    private String documentNumber;

    @JsonProperty("accountNumber")
    private Integer accountNumber;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("agencyNumber")
    private Integer agencyNumber;
    @JsonProperty("nameClient")
    private String nameClient;

    @JsonProperty("lastNameClient")
    private String lastNameClient;

    @JsonProperty("pixType")
    private String pixType;

    @JsonProperty("pixValue")
    private String pixValue;

    public Pix toPix() {

        Account account = new Account.Builder()
                .document(new Document(documentNumber))
                .accountType(AccountType.valueOfOrThrow(this.accountType))
                .accountNumber(new AccountNumber(this.accountNumber))
                .agencyNumber(new AgencyNumber(this.agencyNumber))
                .build();

        PixType pixTypeDomain = PixType.valueOf(this.pixType);

        PixValue pixValueDomain = new PixValue(this.pixValue, pixTypeDomain);

        return new Pix.Builder()
                .account(account)
                .pixType(pixTypeDomain)
                .pixValue(pixValueDomain)
                .build();

    }








}
