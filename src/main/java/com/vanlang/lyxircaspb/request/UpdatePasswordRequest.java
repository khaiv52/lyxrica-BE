package com.vanlang.lyxircaspb.request;

public class UpdatePasswordRequest {
    String email;
    String password;

    public UpdatePasswordRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UpdatePasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

