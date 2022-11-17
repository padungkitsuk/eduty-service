package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class AttachDetail {
    private String detail1;
    private String detail2;
    private String detail3;
    private String detail4;
    private String detail5;
    private String detail6;
    private String detail7;
    private String detail8;
    private Date date;
    private Double amount;
    private Double amount1;
    private Double amount2;
    private Double amount3;
    private Double amount4;
    private Integer number;
    private String actionType;
    private String actionType1;
    private ArrayList<ArrayDetail> arrayDetail;

    public AttachDetail() {
    }

    public AttachDetail(String detail1, String detail2, String detail3, String detail4, String detail5, String detail6, String detail7, String detail8, Date date, Double amount, Double amount1, Double amount2, Double amount3, Double amount4, Integer number, String actionType, String actionType1, ArrayList<ArrayDetail> arrayDetail) {
        this.detail1 = detail1;
        this.detail2 = detail2;
        this.detail3 = detail3;
        this.detail4 = detail4;
        this.detail5 = detail5;
        this.detail6 = detail6;
        this.detail7 = detail7;
        this.detail8 = detail8;
        this.date = date;
        this.amount = amount;
        this.amount1 = amount1;
        this.amount2 = amount2;
        this.amount3 = amount3;
        this.amount4 = amount4;
        this.number = number;
        this.actionType = actionType;
        this.actionType1 = actionType1;
        this.arrayDetail = arrayDetail;
    }
}
