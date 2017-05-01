package com.organize4event.organize.controllers;


import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.services.NotificationService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NotificationControll extends Controll {
    public NotificationControll(Context context) {
        super(context);
    }

    public void getUserNotifications(int user_id, final ControllResponseListener listener){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.getUserNotifications(user_id).enqueue(new Callback<ArrayList<UserNotification>>() {
            @Override
            public void onResponse(Response<ArrayList<UserNotification>> response, Retrofit retrofit) {
                ArrayList<UserNotification> userNotifications = (ArrayList<UserNotification>) response.body();
                listener.success(userNotifications);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUserNotification(UserNotification userNotification, int is_read, final ControllResponseListener listener ){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.saveUserNotification(userNotification.getUser(),
                                     userNotification.getBrief_description(),
                                     userNotification.getDescription(),
                                     is_read,
                                     userNotification.getNotification_date()).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Response<UserNotification> response, Retrofit retrofit) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if(error == null){
                    listener.success(userNotification);
                }else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                listener.fail(new Error(t.getMessage()));

            }
        });

    }

    public void readUserNotification(UserNotification userNotification , int is_read , final ControllResponseListener listener){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.readUserNotification(userNotification.getId(), is_read).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Response<UserNotification> response, Retrofit retrofit) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if(error == null){
                    listener.success(userNotification);
                }else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                listener.fail(new Error(t.getMessage()));

            }

        });
    }













}
