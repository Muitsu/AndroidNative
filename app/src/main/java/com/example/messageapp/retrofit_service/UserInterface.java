package com.example.messageapp.retrofit_service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInterface {

    @GET("/users")
    Call<List<UserModel.User>> getAllUser();


    @GET("/users/{id}")
    Call<UserModel.User> getUserById(@Path("id") int userId);

}
