package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.models.UserPrivacy;
import com.organize4event.organize.services.PrivacyService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PrivacyControll extends Controll {
    public PrivacyControll(Context context) {
        super(context);
    }

    public void getPrivacy(String locale, final ControllResponseListener listener){
        PrivacyService service = ApiClient.getRetrofit().create(PrivacyService.class);
        service.getPrivacy(locale).enqueue(new Callback<ArrayList<Privacy>>() {
            @Override
            public void onResponse(Response<ArrayList<Privacy>> response, Retrofit retrofit) {
                ArrayList<Privacy> privacies = (ArrayList<Privacy>) response.body();
                Error error = parserError(privacies.get(0));
                if (error == null){
                    listener.sucess(privacies);
                }
                else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUserPrivacy(UserPrivacy userPrivacy, int checking, final ControllResponseListener listener){
        PrivacyService service = ApiClient.getRetrofit().create(PrivacyService.class);
        service.saveUserPrivacy(userPrivacy.getUser().getId(), userPrivacy.getPrivacy().getId(), checking).enqueue(new Callback<UserPrivacy>() {
            @Override
            public void onResponse(Response<UserPrivacy> response, Retrofit retrofit) {
                UserPrivacy userPrivacy = (UserPrivacy) response.body();
                Error error = parserError(userPrivacy);
                if (error == null){
                    listener.sucess(userPrivacy);
                }
                else{
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
