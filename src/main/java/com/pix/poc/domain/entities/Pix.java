package com.pix.poc.domain.entities;



import com.pix.poc.domain.vo.PixValue;

import java.util.UUID;

public class Pix {

    private String uniqueID;
    private PixType pixType;
    private PixValue pixValue;
    private Account account;


    public Pix(Account account, PixType pixType, PixValue pixValue) {
        this.uniqueID = UUID.randomUUID().toString();
        this.account = account;
        this.pixType = pixType;
        this.pixValue = pixValue;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PixValue getPixValue() {
        return pixValue;
    }

    public void setPixValue(PixValue pixValue) {
        this.pixValue = pixValue;
    }

    public PixType getPixType() {
        return pixType;
    }

    public void setPixType(PixType pixType) {
        this.pixType = pixType;
    }
}
