package com.example.messageapp.api_service;

public class ApiUtil<T> {
    public ApiUtil.Pre pre;
    public ApiUtil.Success<T> success;
    public ApiUtil.Error error;

    public ApiUtil(ApiUtil.Pre pre, ApiUtil.Success<T> success, ApiUtil.Error error) {
        this.pre = pre;
        this.success = success;
        this.error = error;
    }

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
