package com.immortalidiot.studentapp.db;

import com.immortalidiot.studentapp.requests.LoginRequest;
import com.immortalidiot.studentapp.requests.StudentRequests;
import com.immortalidiot.studentapp.requests.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceAPI {

    @POST("/api/v1/student/register")
    Call<StudentRequests> createStudent(@Body StudentRequests register);

    @POST("api/v1/student/authenticate")
    Call<StudentResponse> getStudent(@Body LoginRequest login);
}
