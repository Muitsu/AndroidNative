package com.example.messageapp.api_service;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
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

    // Asynchronous POST request for File
    public void postForFile(Context context, String endpoint, JsonObject body, Map<String, String> headers, boolean includeToken, OnPre pre, OnBinarySuccess onSuccess, OnFail onFailed) {
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                File result = executeRequestWithBodyForFile(context, "POST", baseUrl + endpoint, body, mHeader);
                if (result != null) {
                    runOnMainThread(() -> onSuccess.onSuccess(result));
                } else {
                    runOnMainThread(() -> onFailed.onFailure(new IOException("No data received")));
                }
            } catch (IOException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    // Asynchronous GET request for File
    public void getForFile(Context context, String endpoint, Map<String, String> headers, boolean includeToken, OnPre pre, OnBinarySuccess onSuccess, OnFail onFailed) {
        //runOnMainThread(() -> pre.onPreExecute());
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                File result = executeGetFile(context, baseUrl + endpoint, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException | ApiException e) {
                runOnMainThread(() -> onFailed.onFailure(e));
            }
        });
    }

    public void getForFileCustomUrl(Context context, String url, Map<String, String> headers, boolean includeToken, OnPre pre, OnBinarySuccess onSuccess, OnFail onFailed) {
        //runOnMainThread(() -> pre.onPreExecute());
        runOnMainThread(pre::onPreExecute);
        final String token = getAuthToken(includeToken);
        final Map<String, String> mHeader = mergeHeaders(headers, token);
        executorService.execute(() -> {
            try {
                File result = executeGetFile(context, url, mHeader);
                runOnMainThread(() -> onSuccess.onSuccess(result));
            } catch (IOException | ApiException e) {
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
        if (body == null) {
            body = new JsonObject();
        }
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

    // The executeRequestWithBodyForFile method to handle binary responses
    private File executeRequestWithBodyForFile(Context context, String methodType, String url, JsonObject body, Map<String, String> headers) throws IOException {
        MediaType JSON = MediaType.get("application/json");
        if (body == null) {
            body = new JsonObject();
        }
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
        return executeRequestForFile(context, request);
    }

    private File executeRequestForFile(Context context, Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // Return the binary data (PDF content)
                return writeResponseBodyToDisk(context, response.body(), "document");
            } else {
                String errorBody = null;
                if (response.body() != null) {
                    errorBody = response.body().string();  // Read error body as string
                }
                Log.e(TAG, "Request failed with code: " + response.code() +
                        ", message: " + response.message() +
                        ", error body: " + errorBody);
                // Optionally throw or handle the error further
                return null;
            }
        }
    }

    private File executeGetFile(Context context, String url, Map<String, String> headers) throws IOException, ApiException {
        //Http Request Builder for GET
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        //Http Add Header
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }
        Request request = requestBuilder.build();
        return executeRequestForFile(context, request);
    }


    //Convert body into file
    private static File writeResponseBodyToDisk(Context context, ResponseBody body, String name) {
        try {
            File file;
            OutputStream outputStream = null;
            InputStream inputStream = null;

            try {
                // Choose file location based on Android version
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    // Scoped Storage: App-specific directory
                    File appSpecificDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                    file = new File(appSpecificDir, name + ".pdf");
                } else {
                    // Legacy storage: Public Downloads directory
                    File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    file = new File(publicDir, name + ".pdf");
                }

                // Create necessary directories if they don't exist
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                // Write the file
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();
                return file;
            } catch (IOException e) {
                Log.d("ApiClient :", e.toString());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.d("ApiClient writeResponseBodyToDisk :", e.toString());
            return null;
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
        return "";
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

    public interface OnBinarySuccess {
        void onSuccess(File responseData);
    }

    public class ApiException extends Exception {
        public ApiException(String message) {
            super(message);
        }
    }
}
