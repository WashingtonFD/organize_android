package com.organize4event.organize.controlers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.ErrorReturn;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;
import com.organize4event.organize.services.UserService;

import java.io.File;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserControler extends Controler {
    public UserControler(Context context) {
        super(context);
    }

    public void getUserType(String locale, int code_enum, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.getUserType(locale, code_enum).enqueue(new Callback<UserType>() {
            @Override
            public void onResponse(Call<UserType> call, Response<UserType> response) {
                UserType userType = (UserType) response.body();
                Error error = parserError(userType);
                if (error == null) {
                    listener.success(userType);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserType> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void getUserMail(String mail, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.getUserMail(mail).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null) {
                    listener.success(user);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUser(User user, final ControlResponseListener listener) {
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
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null) {
                    listener.success(user);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void updateUserToken(User user, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateUserToken(user.getId(), user.getToken().getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null) {
                    listener.success(user);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void updateUserPrivacy(User user, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateUserPrivacy(user.getId(), user.getPrivacy().getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null) {
                    listener.success(user);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void uploadProfilePicture(User user, File photo, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", photo.getName(), requestBody);

        service.uploadPhoto(user.getId(), file).enqueue(new Callback<ErrorReturn>() {
            @Override
            public void onResponse(Call<ErrorReturn> call, Response<ErrorReturn> response) {
                ErrorReturn errorReturn = (ErrorReturn) response.body();
                Error error = parserError(errorReturn);
                if (error == null) {
                    listener.success(errorReturn);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ErrorReturn> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void updateUserProfilePicture(User user, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateProfileFacebook(user.getId(),
                user.getFull_name(),
                user.getMail(),
                user.getProfile_picture()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                Error error = parserError(user);
                if (error == null) {
                    listener.success(user);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
