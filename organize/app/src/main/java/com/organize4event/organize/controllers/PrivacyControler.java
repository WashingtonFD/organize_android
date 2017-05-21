package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.services.PrivacyService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyControler extends Controler {
    public PrivacyControler(Context context) {
        super(context);
    }

    public void getPrivacy(String locale, final ControllResponseListener listener) {
        PrivacyService service = ApiClient.getRetrofit().create(PrivacyService.class);
        service.getPrivacy(locale).enqueue(new Callback<ArrayList<Privacy>>() {
            @Override
            public void onResponse(Call<ArrayList<Privacy>> call, Response<ArrayList<Privacy>> response) {
                ArrayList<Privacy> privacies = (ArrayList<Privacy>) response.body();
                Error error = parserError(privacies.get(0));
                if (error == null) {
                    listener.success(privacies);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Privacy>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}

