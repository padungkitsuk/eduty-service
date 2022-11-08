package com.streamit.application.dto;

import lombok.Data;

@Data
public class CorrectReq {
    private String batchDate;
    private String lotName;
    private String approveStatus;
    private String id;
    private Paging paging;
}
