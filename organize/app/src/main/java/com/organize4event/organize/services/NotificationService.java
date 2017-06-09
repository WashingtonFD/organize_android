package com.organize4event.organize.services;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NotificationService {

    @GET("notification/user/{user_id}")
    Call<JsonObject> getUserNotifications(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("notification/save")
    Call<JsonObject> saveUserNotification(
            @Field("user") int user_id,
            @Field("brief_description") String brief_description,
            @Field("description") String description,
            @Field("notification_date") String notification_date
    );

    @FormUrlEncoded
    @POST("notification/{notification_id}/read")
    Call<JsonObject> readUserNotification(
            @Path("notification_id") int notification_id,
            @Field("is_read") int is_read
    );
}
