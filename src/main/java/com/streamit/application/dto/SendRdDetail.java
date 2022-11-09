package com.streamit.application.dto;

import lombok.Data;

@Data
public class SendRdDetail {
    private Integer no;
    private String instInfoId;
    private String taxPayerId;
    private String fullName;
    private Double totalDuty;
    private Double totalDubDutyAmount;
    private Double totalPayment;
    private String sendRdStatus;
}
