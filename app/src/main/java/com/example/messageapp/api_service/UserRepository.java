package com.example.messageapp.api_service;

import androidx.annotation.NonNull;

import com.example.messageapp.AppLog;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    // Initialize apiService as a static variable
    private static final UserInterface apiService = ApiClient.getClient().create(UserInterface.class);
    public static void getUsers(ApiClient.SuccessAPI<ResponseBody> onSuccess, ApiClient.ErrorAPI onError) {
        ApiClient.executeCall(apiService.getAllUser(), onSuccess, onError);
    }

}
