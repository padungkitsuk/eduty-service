package com.streamit.application.dto;

import lombok.Data;


import java.util.Date;

@Data
public class PaymentInfo {

    private String ref1;
    private String ref2;
    private Date expireDate;
    private Double totalAmount;
    private Double totalDuty;
    private Double totalSurcharge;
    private Double totalFine;
    private String payInSlipFile;
    private String qrPayment;

    public PaymentInfo() {}

    public PaymentInfo(String ref1, String ref2, Date expireDate, Double totalAmount, Double totalDuty, Double totalSurcharge, Double totalFine, String payInSlipFile, String qrPayment) {
        this.ref1 = ref1;
        this.ref2 = ref2;
        this.expireDate = expireDate;
        this.totalAmount = totalAmount;
        this.totalDuty = totalDuty;
        this.totalSurcharge = totalSurcharge;
        this.totalFine = totalFine;
        this.payInSlipFile = payInSlipFile;
        this.qrPayment = qrPayment;
    }
}
