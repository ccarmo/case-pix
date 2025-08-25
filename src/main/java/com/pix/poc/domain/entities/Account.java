package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;

public class Account {

    private AccountType accountType;
    private AccountNumber accountNumber;
    private AgencyNumber agencyNumber;
    private String name;
    private String lastName;

    private Account(Builder builder) {
        this.accountType = builder.accountType;
        this.accountNumber = builder.accountNumber;
        this.agencyNumber = builder.agencyNumber;
        this.name = builder.name;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private AccountType accountType;
        private AccountNumber accountNumber;
        private AgencyNumber agencyNumber;
        private String name;
        private String lastName;

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
}
