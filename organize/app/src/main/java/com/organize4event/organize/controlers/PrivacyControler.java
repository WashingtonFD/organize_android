package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.services.PrivacyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyControler extends Controler {
    public PrivacyControler(Context context) {
        super(context);
    }

    public void getPrivacy(String locale, final ControlResponseListener listener) {
        PrivacyService service = ApiClient.getRetrofit().create(PrivacyService.class);
        service.getPrivacy(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<Privacy> privacies = (List<Privacy>) ApiClient.createGson().fromJson(array, new TypeToken<List<Privacy>>() {
                        }.getType());
                        listener.success(privacies);
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

