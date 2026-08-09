package com.KaanIsmetOkul.Spendid.dto;

public class PasswordForgotRequest {
    private String email;

    public PasswordForgotRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
