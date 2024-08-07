package com.vanlang.lyxircaspb.reponse;

public class AuthResponse {

    private String jwt;
    private String message;

    public AuthResponse(String message, String jwt) {
        this.message = message;
        this.jwt = jwt;
    }

    public AuthResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
