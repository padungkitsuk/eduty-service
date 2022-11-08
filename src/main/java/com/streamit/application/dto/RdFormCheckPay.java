package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RdFormCheckPay {


    private Integer formStatus;
    private ArrayList<InstInfoCheckPay> instInfo;


    public RdFormCheckPay() {}

    public RdFormCheckPay(Integer formStatus, ArrayList<InstInfoCheckPay> instInfo) {
        this.formStatus = formStatus;
        this.instInfo = instInfo;
    }
}
