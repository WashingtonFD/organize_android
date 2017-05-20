package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;
import com.organize4event.organize.services.UserService;

import java.io.File;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                    listener.success(userType);
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

    public void saveUser(User user, final ControllResponseListener listener){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.saveUser(user.getUser_type().getId(),
                user.getPlan().getId(),
                user.getPrivacy().getId(),
                user.getFull_name(),
                user.getMail(),
                user.getPassword(),
                user.getCpf(),
                simpleDateFormat.format(user.getBirth_date()),
                user.getGender()
        ).enqueue(new Callback<User>() {
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

    public void updateUserToken(User user, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateUserToken(user.getId(), user.getToken().getId()).enqueue(new Callback<User>() {
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

    public void updateUserPrivacy(User user, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateUserPrivacy(user.getId(), user.getPrivacy().getId()).enqueue(new Callback<User>() {
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

    public void uploadProfilePicture(User user, File profile_picture, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profile_picture);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", profile_picture.getName(), requestFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), profile_picture.getName());

        service.uploadProfilePicture(user.getId(), body, name).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = (User)response.body();
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

    public void updateUserProfilePicture(User user, final ControllResponseListener listener){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateProfileFacebook(user.getId(),
                user.getFull_name(),
                user.getMail(),
                user.getProfile_picture()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = (User)response.body();
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
}
