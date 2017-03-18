package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;
import com.organize4event.organize.services.UserService;

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
}
