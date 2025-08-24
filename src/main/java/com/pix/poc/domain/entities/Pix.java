package com.pix.poc.domain.entities;

import com.pix.poc.domain.vo.PixValue;

public class Pix {

    private PixType pixType;
    private PixValue pixValue;
    private Account account;


    public Pix(Account account, PixType pixType, PixValue pixValue) {
        this.account = account;
        this.pixType = pixType;
        this.pixValue = pixValue;
    }
}
