package com.pix.poc.interactors.database.model;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "account_number", referencedColumnName = "account_number"),
            @JoinColumn(name = "agency_number", referencedColumnName = "agency_number")
    })
    private AccountModel account;

    @Column(name = "inclusion_date", nullable = false)
    private LocalDate inclusionDate;

    @Column(name = "inactivation_date")
    private LocalDate inactivationDate;

    public PixModel() {}

    public PixModel(String id, String pixType, String pixValue,
                    AccountModel account, LocalDate inclusionDate, LocalDate inactivationDate) {
        this.id = id;
        this.pixType = pixType;
        this.pixValue = pixValue;
        this.account = account;
        this.inclusionDate = inclusionDate;
        this.inactivationDate = inactivationDate;
    }

    public String getId() { return id; }
    public String getPixType() { return pixType; }
    public String getPixValue() { return pixValue; }
    public AccountModel getAccount() { return account; }
    public LocalDate getInclusionDate() { return inclusionDate; }
    public LocalDate getInactivationDate() { return inactivationDate; }
}
