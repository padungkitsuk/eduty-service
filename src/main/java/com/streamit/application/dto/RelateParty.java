package com.streamit.application.dto;

import lombok.Data;

@Data
public class RelateParty {
    private SpecifiedTaxRegistration specifiedTaxRegistration;
    private String titleName;
    private String name;
    private String surname;
    private String branchNo;
    private String branchType;

    public RelateParty() {
    }

    public RelateParty(SpecifiedTaxRegistration specifiedTaxRegistration, String titleName, String name, String surname, String branchNo, String branchType) {
        this.specifiedTaxRegistration = specifiedTaxRegistration;
        this.titleName = titleName;
        this.name = name;
        this.surname = surname;
        this.branchNo = branchNo;
        this.branchType = branchType;
    }
}
