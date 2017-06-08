package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.services.PlanService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanControler extends Controler {
    public PlanControler(Context context) {
        super(context);
    }

    public void getPlan(String locale, final ControlResponseListener listener) {
        PlanService service = ApiClient.getRetrofit().create(PlanService.class);
        service.getPlan(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonArray array = jsonObject.get("data").getAsJsonArray();
                    List<Plan> plans = (List<Plan>) new Gson().fromJson(array, new TypeToken<List<Plan>>() {
                    }.getType());
                    listener.success(plans);
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

    public void getPlanId(String locale, int code_enum, final ControlResponseListener listener) {
        PlanService service = ApiClient.getRetrofit().create(PlanService.class);
        service.getPLanId(locale, code_enum).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    JsonObject object = jsonObject.get("data").getAsJsonObject();
                    Plan plan = new Gson().fromJson(object, Plan.class);
                    listener.success(plan);
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
