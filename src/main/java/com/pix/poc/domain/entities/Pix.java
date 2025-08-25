package com.pix.poc.domain.entities;



import com.pix.poc.domain.vo.PixValue;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class Pix {

    private String uniqueID;
    private PixType pixType;
    private PixValue pixValue;
    private Account account;
    private LocalDate inclusionDate;
    private LocalDate inactivationDate;


    public Pix(Account account, PixType pixType, PixValue pixValue, LocalDate inclusionDate, LocalDate inactivationDate) {
        this.account = account;
        this.pixType = pixType;
        this.pixValue = pixValue;
        this.inclusionDate = inclusionDate;
        this.inactivationDate = inactivationDate;
    }

    public Pix(Account account, PixType pixType, PixValue pixValue) {
        this.uniqueID = UUID.randomUUID().toString();
        this.account = account;
        this.pixType = pixType;
        this.pixValue = pixValue;
    }

    public String getUniqueID() {
        return uniqueID;
    }


    public Account getAccount() {
        return account;
    }

    public PixValue getPixValue() {
        return pixValue;
    }


    public PixType getPixType() {
        return pixType;
    }

    public LocalDate getInclusionDate() {
        return inclusionDate;
    }

    public LocalDate getInactivationDate() {
        return inactivationDate;
    }
}
