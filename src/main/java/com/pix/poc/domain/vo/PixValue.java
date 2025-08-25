package com.pix.poc.domain.vo;

import com.pix.poc.domain.entities.PixType;

public class PixValue {

    private String value;

    public PixValue(String value, PixType pixType) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
