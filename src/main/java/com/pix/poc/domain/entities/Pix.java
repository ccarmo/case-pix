package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.PixValue;
import java.time.LocalDate;
import java.util.UUID;

public class Pix {

    private String uniqueID;
    private PixType pixType;
    private PixValue pixValue;
    private Account account;
    private LocalDate inclusionDate;
    private LocalDate inactivationDate;
    private Boolean active;

    private Pix(Builder builder) {
        this.uniqueID = builder.uniqueID != null ? builder.uniqueID : UUID.randomUUID().toString();
        this.account = builder.account;
        this.pixType = builder.pixType;
        this.pixValue = builder.pixValue;
        this.inclusionDate = builder.inclusionDate != null ? builder.inclusionDate : LocalDate.now();
        this.inactivationDate = builder.inactivationDate;
        this.active = builder.active != null ? builder.active : true;
    }

    public static class Builder {
        private String uniqueID;
        private PixType pixType;
        private PixValue pixValue;
        private Account account;
        private LocalDate inclusionDate;
        private LocalDate inactivationDate;
        private Boolean active; // novo campo no builder

        public Builder account(Account account) { this.account = account; return this; }
        public Builder pixType(PixType pixType) { this.pixType = pixType; return this; }
        public Builder pixValue(PixValue pixValue) { this.pixValue = pixValue; return this; }
        public Builder uniqueID(String uniqueID) { this.uniqueID = uniqueID; return this; }
        public Builder inclusionDate(LocalDate date) { this.inclusionDate = date; return this; }
        public Builder inactivationDate(LocalDate date) { this.inactivationDate = date; return this; }
        public Builder active(Boolean active) { this.active = active; return this; }

        public Pix build() { return new Pix(this); }
    }


    public String getUniqueID() { return uniqueID; }
    public Account getAccount() { return account; }
    public PixValue getPixValue() { return pixValue; }
    public PixType getPixType() { return pixType; }
    public LocalDate getInclusionDate() { return inclusionDate; }
    public LocalDate getInactivationDate() { return inactivationDate; }
    public Boolean isActive() { return active; }
    public void setAccount(Account account) {
        this.account = account;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setInactivationDate(LocalDate inactivationDate){
        this.inactivationDate = inactivationDate;
    }

}
