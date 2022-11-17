package com.streamit.application.dto;

import lombok.Data;

@Data
public class TaxPayer {
    private SpecifiedTaxRegistration specifiedTaxRegistration;
    private String branchNo;
    private String branchType;
    private String relationship;

    public TaxPayer() {
    }

    public TaxPayer(SpecifiedTaxRegistration specifiedTaxRegistration, String branchNo, String branchType, String relationship) {
        this.specifiedTaxRegistration = specifiedTaxRegistration;
        this.branchNo = branchNo;
        this.branchType = branchType;
        this.relationship = relationship;
    }
}
