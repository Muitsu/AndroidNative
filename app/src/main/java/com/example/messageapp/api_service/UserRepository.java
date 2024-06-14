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

    public static void getUser(SuccessCallback successCallback, ErrorCallback errorCallback) {
        Call<ResponseBody> call = apiService.getAllUser();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful() && responseBody != null) {
                        String responseData = responseBody.string();
                        successCallback.onSuccess(responseData);
                    } else {
                        errorCallback.onError("Unsuccessful response: " + response.code());
                    }
                } catch (IOException e) {
                    errorCallback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                errorCallback.onError(t.getMessage());
            }
        });

    }

    @FunctionalInterface
    public interface SuccessCallback {
        void onSuccess(String responseData);
    }

    @FunctionalInterface
    public interface ErrorCallback {
        void onError(String errorMessage);
    }
}
