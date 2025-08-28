package com.pix.poc.infra.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class AccountModel {

    @EmbeddedId
    private AccountId id;

    @Column(name = "account_type", length = 10, nullable = false)
    private String accountType;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "document_number", length = 14, nullable = false)
    private String documentNumber;

    public AccountModel() {}

    public AccountModel(AccountId id, String accountType, String name, String lastName, String documentNumber) {
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
    }

    public AccountId getId() {
        return id;
    }

    public void setId(AccountId id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
