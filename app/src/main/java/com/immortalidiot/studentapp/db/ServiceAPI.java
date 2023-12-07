package com.immortalidiot.studentapp.db;

import com.immortalidiot.studentapp.requests.StudentRequests;
import com.immortalidiot.studentapp.requests.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceAPI {

    @POST("/register")
    Call<StudentRequests> createStudent(@Body StudentRequests student);

    @GET("/auth")
    Call<StudentResponse> getStudent(@Body StudentRequests student);
}
