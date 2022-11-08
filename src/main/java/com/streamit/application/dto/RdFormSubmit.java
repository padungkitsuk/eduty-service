package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RdFormSubmit {

    private String requestId;
    private String apiRefNo;
    private ArrayList<InstInfo> instInfo;
    private PaymentInfo paymentInfo;

    public RdFormSubmit() {}

    public RdFormSubmit(String requestId, String apiRefNo, ArrayList<InstInfo> instInfo, PaymentInfo paymentInfo) {
        this.requestId = requestId;
        this.apiRefNo = apiRefNo;
        this.instInfo = instInfo;
        this.paymentInfo = paymentInfo;
    }
}
