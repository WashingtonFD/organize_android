package com.organize4event.organize.controller;

import android.content.Context;

import com.organize4event.organize.common.ApiClient;
import com.organize4event.organize.common.Constants;
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

    public void saveFirstAccess(FirstAccess firstAccess, ControllResponseListener listener){
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
                //TODO: IMPLEMENTAR ERROR
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
