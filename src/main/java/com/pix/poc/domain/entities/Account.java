package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;

public class Account {

    private Document document;
    private AccountType accountType;
    private AccountNumber accountNumber;
    private AgencyNumber agencyNumber;
    private String name;
    private String lastName;

    private Account(Builder builder) {
        this.document = builder.document;
        this.accountType = builder.accountType;
        this.accountNumber = builder.accountNumber;
        this.agencyNumber = builder.agencyNumber;
        this.name = builder.name;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private Document document;
        private AccountType accountType;
        private AccountNumber accountNumber;
        private AgencyNumber agencyNumber;
        private String name;
        private String lastName;

        public Builder document(Document document) {
            this.document = document;
            return this;
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder accountNumber(AccountNumber accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder agencyNumber(AgencyNumber agencyNumber) {
            this.agencyNumber = agencyNumber;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public AgencyNumber getAgencyNumber() {
        return agencyNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Document getDocument() {
        return document;
    }
}
