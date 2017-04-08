package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;
import com.organize4event.organize.services.UserService;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserControll extends Controll {
    public UserControll(Context context) {
        super(context);
    }

    public void getUserType(String locale, int code_enum, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.getUserType(locale, code_enum).enqueue(new Callback<UserType>() {
            @Override
            public void onResponse(Response<UserType> response, Retrofit retrofit) {
                UserType userType = (UserType) response.body();
                Error error = parserError(userType);
                if (error == null){
                    listener.sucess(userType);
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

    public void getUser(User user, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.getUser(user.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null){
                    listener.sucess(user);
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

    public void saveUser(User user, int term_accept, final ControllResponseListener listener){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.saveUser(user.getUser_type().getId(),
                user.getFull_name(),
                user.getCpf(),
                user.getMail(),
                simpleDateFormat.format(user.getBirth_date()),
                user.getGender(),
                user.getPassword(),
                user.getTerm().getId(),
                term_accept,
                fullDateFormat.format(user.getTerm_accept_date()),
                user.getPlan().getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null){
                    PreferencesManager.saveUser(user);
                    AppApplication.setUser(user);
                    listener.sucess(user);
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
