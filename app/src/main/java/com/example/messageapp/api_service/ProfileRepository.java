package com.example.messageapp.api_service;

import com.example.messageapp.AppLog;
import com.example.messageapp.retrofit_service.UserModel;

public class ProfileRepository {
    final static ApiClient client = new ApiClient();

    public static void getProfile(ApiUtil.Pre pre, ApiUtil.Success<UserModel.User> success, ApiUtil.Error error) {

        client.get("/users", null, false,
                () -> {
                    pre.onPreExecute();
                }, (response) -> {
                    try {
                        UserModel.User data = UserModel.User.fromJson(response);
                        success.onSuccess(data);
                    } catch (Exception e) {
                        error.onError(e.toString());
                    }
                }, (fail) -> {
                    error.onError(fail.toString());
                });
    }
}