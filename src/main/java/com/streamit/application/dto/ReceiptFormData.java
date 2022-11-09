package com.streamit.application.dto;

import lombok.Data;

@Data
public class ReceiptFormData {
    private Integer no;
    private String id;
    private String batchDate;
    private String batchTime;
    private String lotName;
    private Integer totalDoc;
    private Double totalPayment;

}
