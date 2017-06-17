package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SettingsService {
    @GET("settings/{locale}")
    Call<JsonObject> getSettings(@Path("locale") String locale);

    @GET("user_settings/user/{user_id}")
    Call<JsonObject> getUserSettings(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("user_settings/save")
    Call<JsonObject> saveUserSetting(
            @Field("user") int user_id,
            @Field("setting") int setting_id,
            @Field("checking") int checking,
            @Field("value") int value);

    @FormUrlEncoded
    @POST("user_settings/{user_setting_id}/checking")
    Call<JsonObject> checkingUserSetting(
            @Path("user_setting_id") int user_setting_id,
            @Field("checking") int checking);
}
