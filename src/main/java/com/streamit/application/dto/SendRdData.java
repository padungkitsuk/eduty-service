package com.streamit.application.dto;

import lombok.Data;

@Data
public class SendRdData {
    private Integer no;
    private String id;
    private String batchDate;
    private String batchTime;
    private String lotName;
    private Integer totalDoc;
    private String sendRdDate;
    private String sendRdStatus;
    private Double totalDuty;
    private Double totalDubDutyAmount;
    private Double totalPayment;
    private String paymentStatus;
    private Integer fail;
}
