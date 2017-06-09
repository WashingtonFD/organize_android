package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonObject;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.services.FirstAccessService;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstAccessControler extends Controler {
    public FirstAccessControler(Context context) {
        super(context);
    }

    public void saveFirstAccess(FirstAccess firstAccess, final ControlResponseListener listener) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.saveFirstAccess(
                firstAccess.getUser().getId(),
                firstAccess.getDevice_id(),
                firstAccess.getLocale(),
                simpleDateFormat.format(firstAccess.getInstalation_date())
        ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        FirstAccess firstAccess = ApiClient.createGson().fromJson(object, FirstAccess.class);
                        PreferencesManager.saveFirstAccess(firstAccess);
                        AppApplication.setFirstAccess(firstAccess);
                        listener.success(firstAccess);
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

    public void getFirstAccess(String device_id, final ControlResponseListener listener) {
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.getFirstAccess(device_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        FirstAccess firstAccess = ApiClient.createGson().fromJson(object, FirstAccess.class);
                        listener.success(firstAccess);
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

    public void getAccessPlatform(String locale, int code_enum, final ControlResponseListener listener) {
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.getAccessPlatform(locale, code_enum).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        AccessPlatform accessPlatform = ApiClient.createGson().fromJson(object, AccessPlatform.class);
                        listener.success(accessPlatform);
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
