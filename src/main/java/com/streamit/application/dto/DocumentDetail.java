package com.streamit.application.dto;

import lombok.Data;

@Data
public class DocumentDetail {
    private String requestId;
    private String formType;
    private String typeCode;
    private String version;
    private Integer transAmount;

    public DocumentDetail() {
    }

    public DocumentDetail(String requestId, String formType, String typeCode, String version, Integer transAmount) {
        this.requestId = requestId;
        this.formType = formType;
        this.typeCode = typeCode;
        this.version = version;
        this.transAmount = transAmount;
    }
}
