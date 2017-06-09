package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonObject;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;
import com.organize4event.organize.services.TokenService;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenControler extends Controler {
    public TokenControler(Context context) {
        super(context);
    }

    public void getLoginType(String locale, int code_enum, final ControlResponseListener listener) {
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.getLoginType(locale, code_enum).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonObject object = jsonObject.get("data").getAsJsonObject();
                    LoginType loginType = ApiClient.createGson().fromJson(object, LoginType.class);
                    listener.success(loginType);
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

    public void login(String mail, final String password, final ControlResponseListener listener) {
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.login(mail, password).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
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

    public void saveToken(Token token, int user_id, int keep_logged, final ControlResponseListener listener) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.saveToken(
                user_id,
                token.getLogin_type().getId(),
                token.getAccess_platform().getId(),
                fullDateFormat.format(token.getAccess_date()),
                keep_logged).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        Token token = ApiClient.createGson().fromJson(object, Token.class);
                        listener.success(token);
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
}
