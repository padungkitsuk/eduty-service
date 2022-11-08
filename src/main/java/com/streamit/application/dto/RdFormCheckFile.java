package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RdFormCheckFile {

    private String apiRefNo;
    private ArrayList<InstInfo> instInfo;
    private PaymentInfo paymentInfo;

    public RdFormCheckFile() {}

    public RdFormCheckFile(String apiRefNo, ArrayList<InstInfo> instInfo, PaymentInfo paymentInfo) {
        this.apiRefNo = apiRefNo;
        this.instInfo = instInfo;
        this.paymentInfo = paymentInfo;
    }
}
