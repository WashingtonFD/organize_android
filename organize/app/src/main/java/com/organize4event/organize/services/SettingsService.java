package com.organize4event.organize.services;

import com.organize4event.organize.models.Setting;
import com.organize4event.organize.models.UserSetting;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SettingsService {
    @GET("setting/{locale}")
    Call<ArrayList<Setting>> getSettings(@Path("locale") String locale);

    @GET("user_settings/user/{user_id}")
    Call<ArrayList<UserSetting>> getUserSettings(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("user_settings/save")
    Call<UserSetting> saveUserSetting(
            @Field("user") int user_id,
            @Field("setting") int setting_id,
            @Field("checking") int checking,
            @Field("value") int value);

    @FormUrlEncoded
    @POST("user_settings/{user_setting_id}/checking")
    Call<UserSetting> checkingUserSetting(
            @Path("user_setting_id") int user_setting_id,
            @Field("checking") int checking);
}
