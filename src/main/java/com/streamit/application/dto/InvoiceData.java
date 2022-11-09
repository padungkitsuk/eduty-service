package com.streamit.application.dto;

import lombok.Data;

@Data
public class InvoiceData {
    private Integer no;
    private String id;
    private String batchDate;
    private String batchTime;
    private String lotName;
    private Integer totalDoc;
    private Double totalDuty;
    private Double totalDubDutyAmount;
    private Double totalPayment;
    private String ref1;
    private String ref2;
    private String paymentStatus;

}
