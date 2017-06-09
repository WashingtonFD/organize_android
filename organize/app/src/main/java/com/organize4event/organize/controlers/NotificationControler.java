package com.organize4event.organize.controlers;


import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.services.NotificationService;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationControler extends Controler {
    public NotificationControler(Context context) {
        super(context);
    }

    public void getUserNotifications(int user_id, final ControlResponseListener listener) {
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.getUserNotifications(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<UserNotification> userNotifications = (List<UserNotification>) ApiClient.createGson().fromJson(array, new TypeToken<List<UserNotification>>() {
                        }.getType());
                        listener.success(userNotifications);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
                simpleDateFormat.format(userNotification.getNotification_date())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        UserNotification userNotification = ApiClient.createGson().fromJson(object, UserNotification.class);
                        listener.success(userNotification);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void readUserNotification(UserNotification userNotification, int is_read, final ControlResponseListener listener) {
        NotificationService service = ApiClient.getRetrofit().create(NotificationService.class);
        service.readUserNotification(userNotification.getId(), is_read).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        UserNotification userNotification = ApiClient.createGson().fromJson(object, UserNotification.class);
                        listener.success(userNotification);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
