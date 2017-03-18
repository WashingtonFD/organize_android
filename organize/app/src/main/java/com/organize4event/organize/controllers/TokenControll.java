package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.services.TokenService;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TokenControll extends Controll{
    public TokenControll(Context context) {
        super(context);
    }

    public void getLoginType(String locale, int code_enum, final ControllResponseListener listener){
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.getLoginType(locale, code_enum).enqueue(new Callback<LoginType>() {
            @Override
            public void onResponse(Response<LoginType> response, Retrofit retrofit) {
                LoginType loginType = (LoginType) response.body();
                Error error = parserError(loginType);
                if (error == null){
                    listener.sucess(loginType);
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

    public void getToken(FirstAccess firstAccess, final ControllResponseListener listener){
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.getToken(firstAccess.getId()).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                Token token = (Token) response.body();
                Error error = parserError(token);
                if (error == null){
                    listener.sucess(token);
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
}
