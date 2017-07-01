package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonObject;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
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
        service.getUserType(locale, code_enum).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("GET USERT TYPE", jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        UserType userType = ApiClient.createGson().fromJson(object, UserType.class);
                        listener.success(userType);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void getUserMail(String mail, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.getUserMail(mail).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("GET USER MAIL", jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        User user = ApiClient.createGson().fromJson(object, User.class);
                        listener.success(user);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
        ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("SAVE USER", jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        User user = ApiClient.createGson().fromJson(object, User.class);
                        listener.success(user);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void updateUserPrivacy(User user, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateUserPrivacy(user.getId(), user.getToken().getAccess_token(), user.getPrivacy().getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("UPDATE PRIVACY", jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        User user = ApiClient.createGson().fromJson(object, User.class);
                        listener.success(user);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void uploadProfilePicture(User user, File photo, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), photo);
        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", photo.getName(), requestBody);

        service.uploadPhoto(user.getId(), file).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("UPLOAD PICTURE", jsonObject);
                if (error == null) {
                    listener.success(jsonObject);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void updateUserFacebook(User user, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.updateProfileFacebook(user.getId(),
                user.getToken().getAccess_token(),
                user.getFull_name(),
                user.getMail(),
                user.getProfile_picture()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("UPDATE USER FACEBOOK", jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        User user = ApiClient.createGson().fromJson(object, User.class);
                        listener.success(user);
                    }
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void validateUser(String email, final ControlResponseListener listener) {
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        service.validateUser(email).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError("VALIDATE EMAIL", jsonObject);
                if (error == null) {
                    listener.success(true);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
