package com.example.messageapp.retrofit_service;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getClient(String baseUrl, final Map<String, String> headers, boolean isByPassSSL) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Call the method to bypass SSL if the flag is true
        if (isByPassSSL) {
            bypassSSLValidation(httpClient);
        }

        if (headers != null && !headers.isEmpty()) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                headers.forEach(requestBuilder::header);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        }
        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl != null ? baseUrl : BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    // Private method to configure SSL bypass
    private static void bypassSSLValidation(OkHttpClient.Builder httpClient) {
        try {
            @SuppressLint("CustomX509TrustManager") TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            httpClient.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier((hostname, session) -> true);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static Retrofit getClient() {
        return getClient(null, null, false);
    }

    //Client with byPass SSL
    public static Retrofit getClient(boolean isByPassSSL) {
        return getClient(null, null, isByPassSSL);
    }

    //Client with baseUrl
    public static Retrofit getClient(String baseUrl) {
        return getClient(baseUrl, null, false);
    }

    //Client with [baseUrl,byPass SSL]
    public static Retrofit getClient(String baseUrl, boolean isByPassSSL) {
        return getClient(baseUrl, null, isByPassSSL);
    }

    //Client with [headers]
    public static Retrofit getClientWithHeaders(Map<String, String> headers) {
        return getClient(null, headers, false);
    }

    //Client with [headers,byPass SSL]
    public static Retrofit getClientWithHeaders(Map<String, String> headers, boolean isByPassSSL) {
        return getClient(null, headers, isByPassSSL);
    }

    //Client with [baseUrl, headers]
    public static Retrofit getClientWithHeaders(String baseUrl, Map<String, String> headers) {
        return getClient(baseUrl, headers, false);
    }

    //Client with [baseUrl, headers,byPass SSL]
    public static Retrofit getClientWithHeaders(String baseUrl, Map<String, String> headers, boolean isByPassSSL) {
        return getClient(baseUrl, headers, isByPassSSL);
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
