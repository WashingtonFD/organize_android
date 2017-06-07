package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PrivacyService {
    @GET("privacy/{locale}")
    Call<JsonObject> getPrivacy(@Path("locale") String locale);
}
