package com.streamit.application.dto;

import lombok.Data;

@Data
public class EInstrumentCheckPay {

    private String responseCode;
    private String responsMessage;
    private RdFormCheckPay rdForm;

    public EInstrumentCheckPay() {}

    public EInstrumentCheckPay(String responseCode, String responsMessage, RdFormCheckPay rdForm) {
        this.responseCode = responseCode;
        this.responsMessage = responsMessage;
        this.rdForm = rdForm;
    }
}
