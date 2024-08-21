package com.example.messageapp.retrofit_service;

import java.util.List;

public class UserRepository {

    // Initialize apiService as a static variable
    private static final UserInterface userRepo = RetrofitClient.getClient().create(UserInterface.class);

    public static void getUsers(RetrofitClient.SuccessAPI<List<UserModel.User>> onSuccess, RetrofitClient.ErrorAPI onError) {
        RetrofitClient.executeCall(userRepo.getAllUser(), onSuccess, onError);
    }

    public static void getUserById(RetrofitClient.SuccessAPI<UserModel.User> onSuccess, RetrofitClient.ErrorAPI onError) {
        RetrofitClient.executeCall(userRepo.getUserById(1), onSuccess, onError);
    }
}