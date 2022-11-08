package com.streamit.application.dto;

import lombok.Data;

@Data
public class InstInfo {

    private String id;
    private String refNo;
    private SpecifiedTaxRegistration specifiedTaxRegistration;

    public InstInfo() {}

    public InstInfo(String id, String refNo, SpecifiedTaxRegistration specifiedTaxRegistration) {
        this.id = id;
        this.refNo = refNo;
        this.specifiedTaxRegistration = specifiedTaxRegistration;
    }
}
