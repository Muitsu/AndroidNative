package com.example.messageapp.api_service;

import androidx.annotation.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    // Generic method to handle Retrofit calls
    public static <T> T executeCall(Call<T> call) throws ExecutionException, InterruptedException {
        CompletableFuture<T> future = new CompletableFuture<>();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new Throwable("Response not successful: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });

        return future.get();
    }
}
