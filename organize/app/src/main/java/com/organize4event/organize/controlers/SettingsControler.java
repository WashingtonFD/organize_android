package com.organize4event.organize.controlers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.Setting;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.services.SettingsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsControler extends Controler {
    public SettingsControler(Context context) {
        super(context);
    }

    public void getSettings(String locale, final ControllResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.getSettings(locale).enqueue(new Callback<ArrayList<Setting>>() {
            @Override
            public void onResponse(Call<ArrayList<Setting>> call, Response<ArrayList<Setting>> response) {
                ArrayList<Setting> settings = (ArrayList<Setting>) response.body();
                Error error = parserError(settings.get(0));
                if (error == null) {
                    listener.success(settings);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Setting>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void getUserSettings(int user_id, final ControllResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.getUserSettings(user_id).enqueue(new Callback<ArrayList<UserSetting>>() {
            @Override
            public void onResponse(Call<ArrayList<UserSetting>> call, Response<ArrayList<UserSetting>> response) {
                ArrayList<UserSetting> userSettings = (ArrayList<UserSetting>) response.body();
                Error error = parserError(userSettings.get(0));
                if (error == null) {
                    listener.success(userSettings);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserSetting>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUserSettings(UserSetting userSetting, int checking, final ControllResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.saveUserSetting(userSetting.getUser(),
                userSetting.getSetting().getId(),
                checking,
                userSetting.getValue()).enqueue(new Callback<UserSetting>() {
            @Override
            public void onResponse(Call<UserSetting> call, Response<UserSetting> response) {
                UserSetting userSetting = (UserSetting) response.body();
                Error error = parserError(userSetting);
                if (error == null) {
                    listener.success(userSetting);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserSetting> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void checkingUserSettings(UserSetting userSetting, int checking, final ControllResponseListener listener) {
        SettingsService service = ApiClient.getRetrofit().create(SettingsService.class);
        service.checkingUserSetting(userSetting.getId(), checking).enqueue(new Callback<UserSetting>() {
            @Override
            public void onResponse(Call<UserSetting> call, Response<UserSetting> response) {
                UserSetting userSetting = (UserSetting) response.body();
                Error error = parserError(userSetting);
                if (error == null) {
                    listener.success(userSetting);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserSetting> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
