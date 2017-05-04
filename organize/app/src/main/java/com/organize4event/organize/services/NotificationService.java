package com.organize4event.organize.services;


import com.organize4event.organize.models.UserNotification;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface NotificationService {

    @GET("notification/user/{user_id}")
    Call<ArrayList<UserNotification>> getUserNotifications(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("notification/save")
    Call<UserNotification> saveUserNotification(
            @Field("user") int user_id,
            @Field("brief_description") String brief_description,
            @Field("description") String description,
            @Field("notification_date") String notification_date
    );

    @FormUrlEncoded
    @POST("notification/{notification_id}/read")
    Call<UserNotification> readUserNotification(
            @Path("notification_id") int notification_id,
            @Field("is_read") int is_read
    );
}
