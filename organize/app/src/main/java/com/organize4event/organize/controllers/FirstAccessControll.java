package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.services.FirstAccessService;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FirstAccessControll extends Controll{
    public FirstAccessControll(Context context) {
        super(context);
    }

    public void saveFirstAccess(FirstAccess firstAccess, final ControllResponseListener listener){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.saveFirstAccess(
                firstAccess.getUser().getId(),
                firstAccess.getDevice_id(),
                firstAccess.getLocale(),
                simpleDateFormat.format(firstAccess.getInstalation_date())
        ).enqueue(new Callback<FirstAccess>() {
            @Override
            public void onResponse(Response<FirstAccess> response, Retrofit retrofit) {
                FirstAccess firstAccess = (FirstAccess) response.body();
                Error error = parserError(firstAccess);
                if (error == null){
                    PreferencesManager.saveFirstAccess(firstAccess);
                    AppApplication.setFirstAccess(firstAccess);
                    listener.success(firstAccess);
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

    public void getFirstAccess(String device_id, final ControllResponseListener listener){
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.getFirstAccess(device_id).enqueue(new Callback<FirstAccess>() {
            @Override
            public void onResponse(Response<FirstAccess> response, Retrofit retrofit) {
                FirstAccess firstAccess = (FirstAccess) response.body();
                Error error = parserError(firstAccess);
                if (error == null){
                    listener.success(firstAccess);
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

    public void getAccessPlatform(String locale, int code_enum, final ControllResponseListener listener){
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.getAccessPlatform(locale, code_enum).enqueue(new Callback<AccessPlatform>() {
            @Override
            public void onResponse(Response<AccessPlatform> response, Retrofit retrofit) {
                AccessPlatform accessPlatform = response.body();
                Error error = parserError(accessPlatform);
                if (error == null){
                    listener.success(accessPlatform);
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
