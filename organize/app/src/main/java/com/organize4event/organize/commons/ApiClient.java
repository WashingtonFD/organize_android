package com.organize4event.organize.commons;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    public ApiClient() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        gsonBuilder.registerTypeAdapter(boolean.class, new BooleanTypeAdapter());
        gsonBuilder.setDateFormat(Constants.FULL_DATE_FORMAT);

        Gson gson = gsonBuilder.create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        String credential = Credentials.basic(Constants.HTTPAUTH_USER, Constants.HTTPAUTH_PASSWORD);
                        return response.request().newBuilder().header("Authorization", credential).build();
                    }
                })
                .build();

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