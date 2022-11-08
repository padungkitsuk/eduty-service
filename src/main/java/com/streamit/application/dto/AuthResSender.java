package com.streamit.application.dto;

import lombok.Data;

@Data
public class AuthResSender {

    private String id;

    public AuthResSender() {}
    public AuthResSender(String id) {
        this.id = id;
    }
}
