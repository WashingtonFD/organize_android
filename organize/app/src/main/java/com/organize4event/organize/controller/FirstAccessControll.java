package com.organize4event.organize.controller;

import android.content.Context;

import com.organize4event.organize.common.ApiClient;
import com.organize4event.organize.common.AppApplication;
import com.organize4event.organize.common.Constants;
import com.organize4event.organize.common.PreferencesManager;
import com.organize4event.organize.listener.ControllResponseListener;
import com.organize4event.organize.model.FirstAccess;
import com.organize4event.organize.service.FirstAccessService;

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
                firstAccess.getDevice_id(),
                firstAccess.getLocale(),
                simpleDateFormat.format(firstAccess.getInstalation_date())
        ).enqueue(new Callback<FirstAccess>() {
            @Override
            public void onResponse(Response<FirstAccess> response, Retrofit retrofit) {
                FirstAccess firstAccess = (FirstAccess) response.body();
                if (firstAccess.getId() > 0){
                    firstAccess.setSucess(true);
                }
                Error error = parserError(firstAccess);
                if (error == null){
                    PreferencesManager.saveFirstAccess(firstAccess);
                    AppApplication.setFirstAccess(firstAccess);
                    listener.sucess(firstAccess);
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
                if (firstAccess == null){
                    firstAccess = new FirstAccess();
                    firstAccess.setSucess(true);
                }
                else if (firstAccess.getId() > 0){
                    firstAccess.setSucess(true);
                }
                Error error = parserError(firstAccess);
                if (error == null){
                    PreferencesManager.saveFirstAccess(firstAccess);
                    AppApplication.setFirstAccess(firstAccess);
                    listener.sucess(firstAccess);
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
