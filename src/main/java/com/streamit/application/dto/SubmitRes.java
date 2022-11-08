package com.streamit.application.dto;

import lombok.Data;

@Data
public class SubmitRes {

    private EInstrumentSubmit eInstrument;

    public SubmitRes() {}

    public SubmitRes(EInstrumentSubmit eInstrument) {
        this.eInstrument = eInstrument;
    }
}
