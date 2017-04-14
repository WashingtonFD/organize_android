package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;
import com.organize4event.organize.services.TokenService;

import java.text.SimpleDateFormat;

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
                    listener.success(loginType);
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
                    listener.success(token);
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

    public void login(String mail, final String password, final ControllResponseListener listener){
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.login(mail, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null){
                    listener.success(user);
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

    public void saveToken(Token token, int keep_logged, final ControllResponseListener listener){
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.saveToken(token.getUser().getId(),
                token.getFirstAccess().getId(),
                token.getLogin_type().getId(),
                token.getAccess_platform().getId(),
                fullDateFormat.format(token.getAccess_date()),
                keep_logged).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                Token token = (Token) response.body();
                Error error = parserError(token);
                if(error == null){
                    PreferencesManager.saveToken(token);
                    AppApplication.setToken(token);
                    PreferencesManager.saveUser(token.getUser());
                    AppApplication.setUser(token.getUser());
                    listener.success(token);
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

    public void updateToken(Token token, int keep_logged, final ControllResponseListener listener){
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.updateToken(token.getId(),
                token.getUser().getId(),
                token.getLogin_type().getId(),
                token.getAccess_platform().getId(),
                fullDateFormat.format(token.getAccess_date()),
                keep_logged).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                Token token = (Token) response.body();
                Error error = parserError(token);
                if (error == null){
                    PreferencesManager.saveToken(token);
                    AppApplication.setToken(token);
                    PreferencesManager.saveUser(token.getUser());
                    AppApplication.setUser(token.getUser());
                    listener.success(token);
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
