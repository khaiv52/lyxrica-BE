package com.vanlang.lyxircaspb.request;

public class EmailRequest {
    private String email;

    public EmailRequest(String email) {
        this.email = email;
    }

    public EmailRequest() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
