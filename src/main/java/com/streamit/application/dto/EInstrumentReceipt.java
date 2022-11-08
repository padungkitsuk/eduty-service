package com.streamit.application.dto;

import lombok.Data;

@Data
public class EInstrumentReceipt {

    private String responseCode;
    private String responsMessage;
    private RdFormReceipt rdForm;

    public EInstrumentReceipt() {}

    public EInstrumentReceipt(String responseCode, String responsMessage, RdFormReceipt rdForm) {
        this.responseCode = responseCode;
        this.responsMessage = responsMessage;
        this.rdForm = rdForm;
    }
}
