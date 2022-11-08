package com.streamit.application.dto;

import lombok.Data;

@Data
public class EInstrumentCheckFile {

    private String responseCode;
    private String responsMessage;
    private RdFormCheckFile rdForm;

    public EInstrumentCheckFile() {}

    public EInstrumentCheckFile(String responseCode, String responsMessage, RdFormCheckFile rdForm) {
        this.responseCode = responseCode;
        this.responsMessage = responsMessage;
        this.rdForm = rdForm;
    }
}
