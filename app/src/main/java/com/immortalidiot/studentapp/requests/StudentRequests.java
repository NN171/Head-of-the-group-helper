package com.immortalidiot.studentapp.requests;

public class StudentRequests {
    private final int studentId;
    private String email;
    private String password;

    public StudentRequests(String email, String password, int studentId) {
        this.email = email;
        this.password = password;
        this.studentId = studentId;
    }

    public int getStudentId() { return studentId; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
