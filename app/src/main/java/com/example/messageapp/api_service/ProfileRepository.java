package com.example.messageapp.api_service;

import com.example.messageapp.AppLog;
import com.example.messageapp.retrofit_service.UserModel;

public class ProfileRepository {
    final static ApiClient client = new ApiClient();

    public static void getProfile(ApiUtil<UserModel.User> callback) {

        client.get("/users", null, false,
                () -> {
                    callback.pre.onPreExecute();
                }, (response) -> {
                    try {
                        UserModel.User data = UserModel.User.fromJson(response);
                        callback.success.onSuccess(data);
                    } catch (Exception e) {
                        callback.error.onError(e.toString());
                    }
                }, (fail) -> {
                    callback.error.onError(fail.toString());
                }
        );
    }
}