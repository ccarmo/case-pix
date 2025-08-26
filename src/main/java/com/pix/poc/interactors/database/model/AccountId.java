package com.pix.poc.interactors.database.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountId implements Serializable {

    @Column(name = "account_number", nullable = false, length = 8)
    private Integer accountNumber;

    @Column(name = "agency_number", nullable = false, length = 4)
    private Integer agencyNumber;

    public AccountId() {}

    public AccountId(Integer accountNumber, Integer agencyNumber) {
        this.accountNumber = accountNumber;
        this.agencyNumber = agencyNumber;
    }

    public Integer getAccountNumber() { return accountNumber; }
    public Integer getAgencyNumber() { return agencyNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId that = (AccountId) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(agencyNumber, that.agencyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agencyNumber);
    }
}
