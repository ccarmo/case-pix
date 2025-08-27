package com.pix.poc.interactors.database.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "pix")
public class PixModel {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "pix_type", length = 20, nullable = false)
    private String pixType;

    @Column(name = "pix_value", length = 77, nullable = false)
    private String pixValue;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "account_number", referencedColumnName = "account_number"),
            @JoinColumn(name = "agency_number", referencedColumnName = "agency_number")
    })
    private AccountModel account;

    @Column(name = "inclusion_date", nullable = false)
    private Instant inclusionDate;

    @Column(name = "inactivation_date")
    private Instant inactivationDate;

    public PixModel() {}

    public PixModel(String id, String pixType, String pixValue,
                    AccountModel account, Instant inclusionDate, Instant inactivationDate, Boolean active) {
        this.id = id;
        this.pixType = pixType;
        this.pixValue = pixValue;
        this.account = account;
        this.inclusionDate = inclusionDate;
        this.inactivationDate = inactivationDate;
        this.active = active;
    }

    public String getId() { return id; }
    public String getPixType() { return pixType; }
    public String getPixValue() { return pixValue; }
    public AccountModel getAccount() { return account; }
    public Instant getInclusionDate() { return inclusionDate; }
    public Instant getInactivationDate() { return inactivationDate; }
    public Boolean getActive() { return active; }
    public void setAccount(AccountModel account) { this.account = account; }

}
