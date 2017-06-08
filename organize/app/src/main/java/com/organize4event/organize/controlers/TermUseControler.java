package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.TermUse;
import com.organize4event.organize.models.UserTerm;
import com.organize4event.organize.services.TermUseService;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermUseControler extends Controler {
    public TermUseControler(Context context) {
        super(context);
    }

    public void getTermUse(final ControlResponseListener listener) {
        TermUseService service = ApiClient.getRetrofit().create(TermUseService.class);
        service.getTermUse().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonObject object = jsonObject.get("data").getAsJsonObject();
                    TermUse termUse = new Gson().fromJson(object, TermUse.class);
                    listener.success(termUse);
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

    public void saveUserTerm(UserTerm userTerm, int term_accept, final ControlResponseListener listener) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TermUseService service = ApiClient.getRetrofit().create(TermUseService.class);
        service.saveUserTerm(userTerm.getUser(),
                userTerm.getTerm().getId(),
                term_accept,
                fullDateFormat.format(userTerm.getTerm_accept_date())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonObject object = jsonObject.get("data").getAsJsonObject();
                    UserTerm userTerm = new Gson().fromJson(object, UserTerm.class);
                    listener.success(userTerm);
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
