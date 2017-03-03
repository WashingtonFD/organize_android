package com.organize4event.organize.controller;

import android.content.Context;

import com.organize4event.organize.common.ApiClient;
import com.organize4event.organize.listener.ControllResponseListener;
import com.organize4event.organize.model.TermUse;
import com.organize4event.organize.service.FirstAccessService;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TermUseControll extends Controll{
    public TermUseControll(Context context) {
        super(context);
    }

    public void getTermUse(final ControllResponseListener listener){
        FirstAccessService service = ApiClient.getRetrofit().create(FirstAccessService.class);
        service.getTermUse().enqueue(new Callback<TermUse>() {
            @Override
            public void onResponse(Response<TermUse> response, Retrofit retrofit) {
                TermUse termUse = (TermUse) response.body();
                Error error = parserError(termUse);
                if(error == null){
                    listener.sucess(termUse);
                }
                else {
                    listener.fail(error);
                    //TODO: IMPLEMENTAR ERROR
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
