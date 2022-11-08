package com.streamit.application.dto;

import lombok.Data;

@Data
public class EInstrumentSubmit {

    private String responseCode;
    private String responsMessage;
    private RdFormSubmit rdForm;

    public EInstrumentSubmit() {}

    public EInstrumentSubmit(String responseCode, String responsMessage, RdFormSubmit rdForm) {
        this.responseCode = responseCode;
        this.responsMessage = responsMessage;
        this.rdForm = rdForm;
    }
}
