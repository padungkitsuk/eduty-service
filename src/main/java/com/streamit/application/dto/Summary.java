package com.streamit.application.dto;

import lombok.Data;

@Data
public class Summary {
    private Double totalDuty;
    private Double totalDupDutyAmount;
    private Double totalPayment;
    private Payer payer;
    private String responsePaymentType;

	public Summary() {
	}

	public Summary(Double totalDuty, Double totalDupDutyAmount, Double totalPayment, Payer payer, String responsePaymentType) {
		this.totalDuty = totalDuty;
		this.totalDupDutyAmount = totalDupDutyAmount;
		this.totalPayment = totalPayment;
		this.payer = payer;
		this.responsePaymentType = responsePaymentType;
	}
}
