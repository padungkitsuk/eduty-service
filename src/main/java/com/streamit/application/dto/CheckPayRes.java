package com.streamit.application.dto;

import lombok.Data;

@Data
public class CheckPayRes {

    private EInstrumentCheckPay eInstrument;

    public CheckPayRes() {}

    public CheckPayRes(EInstrumentCheckPay eInstrument) {
        this.eInstrument = eInstrument;
    }
}
