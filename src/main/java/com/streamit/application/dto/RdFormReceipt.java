package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RdFormReceipt {

    private ArrayList<InstInfoReceipt> instInfo;

    public RdFormReceipt() {}

    public RdFormReceipt(ArrayList<InstInfoReceipt> instInfo) {
        this.instInfo = instInfo;
    }
}
