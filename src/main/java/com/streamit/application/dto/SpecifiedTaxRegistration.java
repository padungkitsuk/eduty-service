package com.streamit.application.dto;

import lombok.Data;

@Data
public class SpecifiedTaxRegistration {
    private String id;

    public SpecifiedTaxRegistration() {
    }

    public SpecifiedTaxRegistration(String id) {
        this.id = id;
    }
}
