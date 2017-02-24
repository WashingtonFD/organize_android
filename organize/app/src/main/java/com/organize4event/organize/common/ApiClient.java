package com.organize4event.organize.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marcelamelo on 20/02/17.
 */

public class ApiClient {
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    public ApiClient() {
        Gson gson = new GsonBuilder().setDateFormat(Constants.FULL_DATE_FORMAT).create();
        OkHttpClient client = new OkHttpClient();
        client.connectTimeoutMillis();
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
