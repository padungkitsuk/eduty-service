package com.streamit.application.dto;

import lombok.Data;

@Data
public class Party {
    private SpecifiedTaxRegistration specifiedTaxRegistration;
    private String titleName;
    private String name;
    private String surname;
    private String branchNo;
    private String branchType;
    private PostalTradeAddress postalTradeAddress;
    private Integer totalParty;

    public Party() {
    }

    public Party(SpecifiedTaxRegistration specifiedTaxRegistration, String titleName, String name, String surname, String branchNo, String branchType, PostalTradeAddress postalTradeAddress, Integer totalParty) {
        this.specifiedTaxRegistration = specifiedTaxRegistration;
        this.titleName = titleName;
        this.name = name;
        this.surname = surname;
        this.branchNo = branchNo;
        this.branchType = branchType;
        this.postalTradeAddress = postalTradeAddress;
        this.totalParty = totalParty;
    }
}
