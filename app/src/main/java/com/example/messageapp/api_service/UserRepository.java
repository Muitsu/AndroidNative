package com.example.messageapp.api_service;

import java.util.List;

public class UserRepository {

    // Initialize apiService as a static variable
    private static final UserInterface userRepo = ApiClient.getClient().create(UserInterface.class);

    public static void getUsers(ApiClient.SuccessAPI<List<UserModel.User>> onSuccess, ApiClient.ErrorAPI onError) {
        ApiClient.executeCall(userRepo.getAllUser(), onSuccess, onError);
    }

    public static void getUserById(ApiClient.SuccessAPI<UserModel.User> onSuccess, ApiClient.ErrorAPI onError) {
        ApiClient.executeCall(userRepo.getUserById(1), onSuccess, onError);
    }
}