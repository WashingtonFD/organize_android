package com.organize4event.organize.controllers;


import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.ErrorReturn;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.services.NotificationService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationControll extends Controll {
    public NotificationControll(Context context) {
        super(context);
    }


    public void getNotification(int notification_id, final ControllResponseListener listener){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.getNotification(notification_id).enqueue(new Callback<ArrayList<UserNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<UserNotification>> call, Response<ArrayList<UserNotification>> response) {
                ArrayList<UserNotification> userNotifications = (ArrayList<UserNotification>) response.body();
                Error error = parserError(userNotifications.get(0));

                if(error == null){
                    listener.success(userNotifications);
                }else {
                    listener.fail(error);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<UserNotification>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));

            }
        });

    }

    public void getUserNotification(int user_id , final ControllResponseListener listener ){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.getUserNotification(user_id).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Call<UserNotification> call, Response<UserNotification> response) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if(error == null){
                    listener.success(userNotification);
                }else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserNotification> call, Throwable t) {
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
            public void onResponse(Call<UserNotification> call, Response<UserNotification> response) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if(error == null){
                    listener.success(userNotification);
                }else {
                    listener.fail(error);
                }

            }

            @Override
            public void onFailure(Call<UserNotification> call, Throwable t) {

                listener.fail(new Error(t.getMessage()));

            }
        });

    }

    public void readUserNotification(UserNotification userNotification , int is_read , final ControllResponseListener listener){
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.readUserNotification(userNotification.getId(), is_read).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Call<UserNotification> call, Response<UserNotification> response) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if(error == null){
                    listener.success(userNotification);
                }else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserNotification> call, Throwable t) {

                listener.fail(new Error(t.getMessage()));

            }
        });
    }













}
