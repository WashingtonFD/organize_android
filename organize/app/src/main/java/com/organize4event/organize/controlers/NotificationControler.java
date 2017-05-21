package com.organize4event.organize.controlers;


import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.services.NotificationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationControler extends Controler {
    public NotificationControler(Context context) {
        super(context);
    }

    public void getUserNotifications(int user_id, final ControlResponseListener listener) {
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.getUserNotifications(user_id).enqueue(new Callback<ArrayList<UserNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<UserNotification>> call, Response<ArrayList<UserNotification>> response) {
                ArrayList<UserNotification> userNotifications = (ArrayList<UserNotification>) response.body();
                listener.success(userNotifications);
            }

            @Override
            public void onFailure(Call<ArrayList<UserNotification>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUserNotification(UserNotification userNotification, final ControlResponseListener listener) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.saveUserNotification(userNotification.getUser(),
                userNotification.getBrief_description(),
                userNotification.getDescription(),
                simpleDateFormat.format(userNotification.getNotification_date())).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Call<UserNotification> call, Response<UserNotification> response) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if (error == null) {
                    listener.success(userNotification);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserNotification> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void readUserNotification(UserNotification userNotification, int is_read, final ControlResponseListener listener) {
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.readUserNotification(userNotification.getId(), is_read).enqueue(new Callback<UserNotification>() {
            @Override
            public void onResponse(Call<UserNotification> call, Response<UserNotification> response) {
                UserNotification userNotification = (UserNotification) response.body();
                Error error = parserError(userNotification);

                if (error == null) {
                    listener.success(userNotification);
                } else {
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
