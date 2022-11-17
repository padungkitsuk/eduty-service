package com.streamit.application.dto;

import lombok.Data;

@Data
public class Payment {
    private Double dutyAmount;
    private Double dupDutyAmount;
    private Double totalAmount;

    public Payment() {
    }

    public Payment(Double dutyAmount, Double dupDutyAmount, Double totalAmount) {
        this.dutyAmount = dutyAmount;
        this.dupDutyAmount = dupDutyAmount;
        this.totalAmount = totalAmount;
    }
}
