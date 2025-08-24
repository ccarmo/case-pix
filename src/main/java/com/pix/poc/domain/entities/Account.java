package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;

public class Account {

    private AccountType accountType;
    private AccountNumber accountNumber;
    private AgencyNumber agencyNumber;

    private Account(Builder builder) {
        this.accountType = builder.accountType;
        this.accountNumber = builder.accountNumber;
        this.agencyNumber = builder.agencyNumber;
    }

    public static class Builder {
        private AccountType accountType;
        private AccountNumber accountNumber;
        private AgencyNumber agencyNumber;

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

        public Account build() {
            return new Account(this);
        }
    }
}
