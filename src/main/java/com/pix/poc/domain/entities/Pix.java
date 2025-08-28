package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.PixId;
import com.pix.poc.domain.vo.PixValue;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.UUID;

public class Pix {

    private PixId uniqueID;
    private PixType pixType;
    private PixValue pixValue;
    private Account account;
    private ZonedDateTime inclusionDate;
    private ZonedDateTime inactivationDate;
    private Boolean active;

    private Pix(Builder builder) {
        this.uniqueID = builder.uniqueID != null ? builder.uniqueID : new PixId(UUID.randomUUID().toString());
        this.account = builder.account;
        this.pixType = builder.pixType;
        this.pixValue = builder.pixValue;
        this.inclusionDate = builder.inclusionDate != null ? builder.inclusionDate : ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        this.inactivationDate = builder.inactivationDate;
        this.active = builder.active != null ? builder.active : true;
    }

    public static class Builder {
        private PixId uniqueID;
        private PixType pixType;
        private PixValue pixValue;
        private Account account;
        private ZonedDateTime inclusionDate;
        private ZonedDateTime inactivationDate;
        private Boolean active; // novo campo no builder

        public Builder account(Account account) { this.account = account; return this; }
        public Builder pixType(PixType pixType) { this.pixType = pixType; return this; }
        public Builder pixValue(PixValue pixValue) { this.pixValue = pixValue; return this; }
        public Builder uniqueID(PixId uniqueID) { this.uniqueID = uniqueID; return this; }
        public Builder inclusionDate(ZonedDateTime date) { this.inclusionDate = date; return this; }
        public Builder inactivationDate(ZonedDateTime date) { this.inactivationDate = date; return this; }
        public Builder active(Boolean active) { this.active = active; return this; }

        public Pix build() { return new Pix(this); }
    }


    public PixId getUniqueID() { return uniqueID; }
    public Account getAccount() { return account; }
    public PixValue getPixValue() { return pixValue; }
    public PixType getPixType() { return pixType; }
    public ZonedDateTime getInclusionDate() { return inclusionDate; }
    public ZonedDateTime getInactivationDate() { return inactivationDate; }
    public Boolean isActive() { return active; }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setInactivationDate(ZonedDateTime inactivationDate){
        this.inactivationDate = inactivationDate;
    }

    public void changeAccount(Account newAccount) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("Não é possível alterar a conta de um PIX inativado.");
        }

        if (newAccount == null) {
            throw new IllegalArgumentException("A nova conta não pode ser nula.");
        }

        if (this.account != null &&
                this.account.getAccountNumber().equals(newAccount.getAccountNumber()) &&
                this.account.getAgencyNumber().equals(newAccount.getAgencyNumber())) {
            return;
        }

        this.account = newAccount;
    }

}
