package com.organize4event.organize.services;


import com.organize4event.organize.models.UserNotification;

import java.util.ArrayList;
import java.util.Date;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationService {

    @GET("notification")
    Call<ArrayList<UserNotification>> getNotification(@Path("notification_id") int notification_id);

    @GET("notification/user/{user_id}")
    Call<UserNotification> getUserNotification(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("notification/save")
    Call<UserNotification> saveUserNotification(
            @Field("user") int user_id,
            @Field("brief_description") String brief_description,
            @Field("description") String description,
            @Field("notification_date") int notification_date,
            @Field("is_read") Date is_read
    );

    @FormUrlEncoded
    @POST("notification/{notification_id}/read")
    Call<UserNotification> readUserNotification(
            @Path("notification_id") int notification_id,
            @Field("is_read") int is_read
    );
}
