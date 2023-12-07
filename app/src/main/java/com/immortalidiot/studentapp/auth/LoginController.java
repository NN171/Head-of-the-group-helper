package com.immortalidiot.studentapp.auth;

import androidx.annotation.NonNull;

import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.StudentRequests;

import retrofit2.*;

public class LoginController {
    ServiceAPI serviceAPI = ClientAPI.getClient().create(ServiceAPI.class);
    StudentRequests requests = new StudentRequests("email@mail.ru", "pas1");
    Call<StudentRequests> requestsCall = serviceAPI.getStudent(requests);

    public void controller() {
        requestsCall.enqueue(new Callback<StudentRequests>() {
            @Override
            public void onResponse(@NonNull Call<StudentRequests> call,
                                   @NonNull Response<StudentRequests> response) {

            }

            @Override
            public void onFailure(@NonNull Call<StudentRequests> call,
                                  @NonNull Throwable t) {

            }
        });
    }
}
