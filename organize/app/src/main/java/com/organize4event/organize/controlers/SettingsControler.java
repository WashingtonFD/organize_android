package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.Setting;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.services.SettingsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsControler extends Controler {
    public SettingsControler(Context context) {
        super(context);
    }

    public void getSettings(String locale, final ControlResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.getSettings(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<Setting> settings = (List<Setting>) ApiClient.createGson().fromJson(array, new TypeToken<List<Setting>>() {
                        }.getType());
                        listener.success(settings);
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

    public void getUserSettings(int user_id, final ControlResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.getUserSettings(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<UserSetting> userSettings = (List<UserSetting>) ApiClient.createGson().fromJson(array, new TypeToken<List<UserSetting>>() {
                        }.getType());
                        listener.success(userSettings);
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

    public void saveUserSettings(UserSetting userSetting, int checking, final ControlResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.saveUserSetting(userSetting.getUser(),
                userSetting.getSetting().getId(),
                checking,
                userSetting.getValue()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        UserSetting userSetting = ApiClient.createGson().fromJson(object, UserSetting.class);
                        listener.success(userSetting);
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

    public void checkingUserSettings(UserSetting userSetting, int checking, final ControlResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.checkingUserSetting(userSetting.getId(), checking).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        UserSetting userSetting = ApiClient.createGson().fromJson(object, UserSetting.class);
                        listener.success(userSetting);
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
