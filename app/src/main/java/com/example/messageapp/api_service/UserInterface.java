package com.example.messageapp.api_service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {

    @GET("/users")
    Call<List<UserModel.User>> getAllUser();


    @GET("/users/{id}")
    Call<UserModel.User> getUserById(@Path("id") int userId);

}
