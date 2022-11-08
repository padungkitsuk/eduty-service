package com.streamit.application.dto;

import lombok.Data;

@Data
public class AuthReq {

    private String username;
    private String password;
    private String nonce;

    public AuthReq() {}

    public AuthReq(String username, String password, String nonce) {
        this.username = username;
        this.password = password;
        this.nonce = nonce;
    }

	
}
