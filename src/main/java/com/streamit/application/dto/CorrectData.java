package com.streamit.application.dto;

import lombok.Data;

@Data
public class CorrectData {
    private Integer no;
    private String id;
    private Integer totalDoc;
    private String lotName;
    private String approveStatus;
    private String approveBy;
    private String updateDate;
    private Double totalDuty;
    private Double totalDubDutyAmount;
    private Double totalPayment;

}
