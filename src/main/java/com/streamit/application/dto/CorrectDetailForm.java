package com.streamit.application.dto;

import lombok.Data;

@Data
public class CorrectDetailForm {
    private String id;
    private Integer no;
    private String instInfoId;
    private String taxPayerId;
    private String fullName;
    private Double totalDuty;
    private Double totalDubDutyAmount;
    private Double totalPayment;
    private String contractNo;
    private String contractStartDate;
    private String contractEndDate;
    private String branchNo;
    private String branchType;
    private String contractWith;
    private String paymentEndDate;
    private CorrectDetailAddress address;

}
