package com.immortalidiot.studentapp.requests;

public class StudentResponse {
    private String token;

    public StudentResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
