package com.streamit.application.dto;

import lombok.Data;

@Data
public class CheckFileRes {

    private EInstrumentCheckFile eInstrument;

    public CheckFileRes() {}

    public CheckFileRes(EInstrumentCheckFile eInstrument) {
        this.eInstrument = eInstrument;
    }
}
