package com.streamit.application.dto;

import lombok.Data;

@Data
public class ReceiptRes {

    private EInstrumentReceipt eInstrument;

    public ReceiptRes() {}

    public ReceiptRes(EInstrumentReceipt eInstrument) {
        this.eInstrument = eInstrument;
    }
}
