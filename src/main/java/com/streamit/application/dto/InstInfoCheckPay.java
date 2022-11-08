package com.streamit.application.dto;

import lombok.Data;

@Data
public class InstInfoCheckPay {


    private String refNo;
    private String instStatus;


    public InstInfoCheckPay() {}

    public InstInfoCheckPay(String refNo, String instStatus) {
        this.refNo = refNo;
        this.instStatus = instStatus;
    }
}
