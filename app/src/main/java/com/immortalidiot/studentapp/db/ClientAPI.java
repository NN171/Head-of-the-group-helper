package com.immortalidiot.studentapp.db;

import okhttp3.OkHttpClient;import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ClientAPI {
    private static final String url = "http://localhost:8080/api/v1/student";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();        }
        return retrofit;
    }

    public static ServiceAPI getServiceApi(){
        return retrofit.create(ServiceAPI.class);
    }
}
