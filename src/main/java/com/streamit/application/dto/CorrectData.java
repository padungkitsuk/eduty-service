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

    public CorrectData() {
    }

    public CorrectData(Integer no, String id, Integer totalDoc, String lotName, String approveStatus, String approveBy, String updateDate, Double totalDuty, Double totalDubDutyAmount, Double totalPayment) {
        this.no = no;
        this.id = id;
        this.totalDoc = totalDoc;
        this.lotName = lotName;
        this.approveStatus = approveStatus;
        this.approveBy = approveBy;
        this.updateDate = updateDate;
        this.totalDuty = totalDuty;
        this.totalDubDutyAmount = totalDubDutyAmount;
        this.totalPayment = totalPayment;
    }
}
