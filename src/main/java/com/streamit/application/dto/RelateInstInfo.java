package com.streamit.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RelateInstInfo {
    private String id;
    private String contractNo;
    private Date creationDate;

    public RelateInstInfo() {
    }

    public RelateInstInfo(String id, String contractNo, Date creationDate) {
        this.id = id;
        this.contractNo = contractNo;
        this.creationDate = creationDate;
    }
}
