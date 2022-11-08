package com.streamit.application.dto;

import lombok.Data;

@Data
public class AuthRes {

    private String responseCode;
    private String responseMessage;
    private String username;
    private String tokenId;
    private AuthResSender sender;

    public AuthRes() {}
    public AuthRes(String responseCode, String responseMessage, String username, String tokenId, AuthResSender sender) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.username = username;
        this.tokenId = tokenId;
        this.sender = sender;
    }
}
