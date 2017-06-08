package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.SecurityQuestion;
import com.organize4event.organize.services.UserSecurityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserSecurityControler extends Controler {
    public UserSecurityControler(Context context) {
        super(context);
    }

    public void getSecurityQuestions(int user_id, final ControlResponseListener listener) {
        UserSecurityService service = ApiClient.getRetrofit().create(UserSecurityService.class);
        service.getSecurityQuestions(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonArray array = jsonObject.get("data").getAsJsonArray();
                    List<SecurityQuestion> securityQuestions = (List<SecurityQuestion>) createGson().fromJson(array, new TypeToken<List<SecurityQuestion>>() {
                    }.getType());
                    listener.success(securityQuestions);
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

    public void sendMail(String mail, int user_security_id, String user_answer, final ControlResponseListener listener) {
        UserSecurityService service = ApiClient.getRetrofit().create(UserSecurityService.class);
        service.sendMail(mail, user_security_id, user_answer).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
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
}
