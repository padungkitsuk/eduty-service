package com.streamit.application.dto;

import lombok.Data;

@Data
public class CorrectReq {
    private String batchDate;
    private String lotName;
    private String approveStatus;
    private String paymentStatus;
    private String sendRdStatus;
    private String refNo;
    private String id;
    private Integer no;
    private Paging paging;
}
