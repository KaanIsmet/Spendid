package com.KaanIsmetOkul.Spendid.dto;

public class LoginResponse {

    private String token;
    private String type = "Bearer";
    private Object user;

    public LoginResponse(String token, Object user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Object getUser() {
        return user;
    }
}
