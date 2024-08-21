package com.example.messageapp.api_service;

public class ApiUtil {
    @FunctionalInterface
    public interface Pre {
        void onPreExecute();
    }

    @FunctionalInterface
    public interface Success<T> {
        void onSuccess(T responseData);
    }

    @FunctionalInterface
    public interface Error {
        void onError(String errorMessage);
    }
}
