package com.streamit.application.dto;

import lombok.Data;

@Data
public class InstInfoReceipt {

    private String refNo;
    private SpecifiedTaxRegistration specifiedTaxRegistration;

    public InstInfoReceipt() {}

    public InstInfoReceipt(String refNo, SpecifiedTaxRegistration specifiedTaxRegistration) {
        this.refNo = refNo;
        this.specifiedTaxRegistration = specifiedTaxRegistration;
    }
}
