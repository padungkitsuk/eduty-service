package com.streamit.application.dto;

import lombok.Data;

@Data
public class PostalTradeAddress {
    private String buildingName;
    private String roomNo;
    private String floorNo;
    private String villageName;
    private String buildingNumber;
    private String moo;
    private String soiName;
    private String junctionName;
    private String streetName;
    private String citySubDivisionName;
    private String cityName;
    private String countrySubDivisionName;
    private String postCode;
    private String countryId;


    public PostalTradeAddress() {
    }

    public PostalTradeAddress(String buildingName, String roomNo, String floorNo, String villageName, String buildingNumber, String moo, String soiName, String junctionName, String streetName, String citySubDivisionName, String cityName, String countrySubDivisionName, String postCode, String countryId) {
        this.buildingName = buildingName;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
        this.villageName = villageName;
        this.buildingNumber = buildingNumber;
        this.moo = moo;
        this.soiName = soiName;
        this.junctionName = junctionName;
        this.streetName = streetName;
        this.citySubDivisionName = citySubDivisionName;
        this.cityName = cityName;
        this.countrySubDivisionName = countrySubDivisionName;
        this.postCode = postCode;
        this.countryId = countryId;
    }
}
