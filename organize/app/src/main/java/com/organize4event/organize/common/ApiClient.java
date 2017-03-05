package com.organize4event.organize.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class ApiClient {
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    public ApiClient() {
        GsonBuilder gsonBuilder =  new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        gsonBuilder.registerTypeAdapter(boolean.class, new BooleanTypeAdapter());
        gsonBuilder.setDateFormat(Constants.FULL_DATE_FORMAT);

        Gson gson = gsonBuilder.create();

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                String credential = Credentials.basic(Constants.HTTPAUTH_USER, Constants.HTTPAUTH_PASSWORD);
                return response.request().newBuilder().header("Authorization", credential).build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
    }

    public static void newInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}