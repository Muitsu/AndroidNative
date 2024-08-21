package com.example.messageapp.api_service;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private static final String TAG = "ApiClient";
    private final OkHttpClient client;

    private final ExecutorService executorService;
    private final Handler mainHandler;

    private final String baseUrl = "https://jsonplaceholder.typicode.com";
    private final Map<String, String> _commonHeaders = new HashMap<String, String>() {{
        put("Content-Type", "application/json");
        put("Accept", "application/json");
    }};

    public ApiClient() {
        client = new OkHttpClient();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    // Asynchronous GET request
    public void get(String endpoint, Map<String, String> headers, boolean includeToken, OnPre pre, OnPost onSuccess, OnFail onFailed) {
        //runOnMainThread(() -> pre.onPreExecute());
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                String result = executeGet(baseUrl + endpoint, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    // Asynchronous POST request
    public void post(String endpoint, JsonObject body, Map<String, String> headers, boolean includeToken, OnPre pre, OnPost onSuccess, OnFail onFailed) {
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                String result = executeRequestWithBody("POST", baseUrl + endpoint, body, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    // Asynchronous PUT request
    public void put(String endpoint, JsonObject body, Map<String, String> headers, boolean includeToken, OnPre pre, OnPost onSuccess, OnFail onFailed) {
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                String result = executeRequestWithBody("PUT", baseUrl + endpoint, body, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    // Asynchronous DELETE request
    public void delete(String endpoint, JsonObject body, Map<String, String> headers, boolean includeToken, OnPre pre, OnPost onSuccess, OnFail onFailed) {
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                String result = executeRequestWithBody("DELETE", baseUrl + endpoint, body, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    //Execute on Background
    private String executeGet(String url, Map<String, String> headers) throws IOException {
        //Http Request Builder for GET
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        //Http Add Header
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }
        Request request = requestBuilder.build();
        return executeRequest(request);
    }

    //Execute on Background
    private String executeRequestWithBody(String methodType, String url, JsonObject body, Map<String, String> headers) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, body.toString());

        //Http Request Builder
        Request.Builder requestBuilder = new Request.Builder().url(url);

        //Http Add Header
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }

        switch (methodType) {
            case "POST":
                requestBuilder.post(requestBody);
                break;
            case "PUT":
                requestBuilder.put(requestBody);
                break;
            case "DELETE":
                requestBuilder.delete(requestBody);
                break;
        }

        Request request = requestBuilder.build();
        return executeRequest(request);
    }

    //Execute on Background
    private String executeRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                Log.e(TAG, "Request failed with code: " + response.code() + " and message: " + response.message());
                return null;
            }
        }
    }

    private Map<String, String> mergeHeaders(Map<String, String> headers, String authToken) {
        Map<String, String> mergedHeaders = new HashMap<>(_commonHeaders);
        if (headers != null) {
            mergedHeaders.putAll(headers);
        }
        if (authToken != null && !authToken.isEmpty()) {
            mergedHeaders.put("Authorization", "Bearer " + authToken);
        }
        return mergedHeaders;
    }

    //GET TOKEN FROM PREFERENCE
    String getAuthToken(boolean includeToken) {
        if (!includeToken) return null;
        // Retrieve and return the token from preferences
        return null;
    }

    //Interceptor between UI thread and Background
    private void runOnMainThread(Runnable runnable) {
        mainHandler.post(runnable);
    }


    public interface OnPre {
        void onPreExecute();
    }

    public interface OnPost {
        void onSuccess(String response);
    }

    public interface OnFail {
        void onFailure(Exception e);
    }

}
