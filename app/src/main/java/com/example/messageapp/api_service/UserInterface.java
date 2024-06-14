package com.example.messageapp.api_service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {

    @GET("/users")
    Call<ResponseBody> getAllUser();


    @GET("/posts/1")
    Call<ResponseBody> getUserById(@Query("UserId") int userId);

}
