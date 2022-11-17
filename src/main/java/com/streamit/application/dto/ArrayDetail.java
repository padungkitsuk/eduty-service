package com.streamit.application.dto;

import lombok.Data;

@Data
public class ArrayDetail {
    private String attType;
    private String attActionType1;
    private String attActionType2;
    private String attDetail1;
    private String attDetail2;
    private String attDetail3;
    private String attDetail4;
    private String attDetail5;
    private String attDetail6;
    private Double attAmount1;
    private Double attAmount2;
    private Double attAmount3;
    private Double attNumber1;

    public ArrayDetail() {
    }

    public ArrayDetail(String attType, String attActionType1, String attActionType2, String attDetail1, String attDetail2, String attDetail3, String attDetail4, String attDetail5, String attDetail6, Double attAmount1, Double attAmount2, Double attAmount3, Double attNumber1) {
        this.attType = attType;
        this.attActionType1 = attActionType1;
        this.attActionType2 = attActionType2;
        this.attDetail1 = attDetail1;
        this.attDetail2 = attDetail2;
        this.attDetail3 = attDetail3;
        this.attDetail4 = attDetail4;
        this.attDetail5 = attDetail5;
        this.attDetail6 = attDetail6;
        this.attAmount1 = attAmount1;
        this.attAmount2 = attAmount2;
        this.attAmount3 = attAmount3;
        this.attNumber1 = attNumber1;
    }
}
