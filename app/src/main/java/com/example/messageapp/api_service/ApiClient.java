package com.example.messageapp.api_service;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl != null ? baseUrl : BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getClient() {
        return getClient(null);
    }

    public static <T> void executeCall(Call<T> call, SuccessAPI<T> success, ErrorAPI errorCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    success.onSuccess(response.body());
                } else {
                    errorCallback.onError("Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                errorCallback.onError(t.getMessage());
            }
        });
    }

    @FunctionalInterface
    public interface SuccessAPI<T> {
        void onSuccess(T responseData);
    }

    @FunctionalInterface
    public interface ErrorAPI {
        void onError(String errorMessage);
    }
}
