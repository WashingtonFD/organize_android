package com.organize4event.organize.controlers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControllResponseListener;
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

    public void getLoginType(String locale, int code_enum, final ControllResponseListener listener) {
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.getLoginType(locale, code_enum).enqueue(new Callback<LoginType>() {
            @Override
            public void onResponse(Call<LoginType> call, Response<LoginType> response) {
                LoginType loginType = (LoginType) response.body();
                Error error = parserError(loginType);
                if (error == null) {
                    listener.success(loginType);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<LoginType> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void login(String mail, final String password, final ControllResponseListener listener) {
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.login(mail, password).enqueue(new Callback<User>() {
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

    public void saveToken(Token token, int user_id, int keep_logged, final ControllResponseListener listener) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TokenService service = ApiClient.getRetrofit().create(TokenService.class);
        service.saveToken(
                user_id,
                token.getLogin_type().getId(),
                token.getAccess_platform().getId(),
                fullDateFormat.format(token.getAccess_date()),
                keep_logged).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = (Token) response.body();
                Error error = parserError(token);
                if (error == null) {
                    listener.success(token);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
