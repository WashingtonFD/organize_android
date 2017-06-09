package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.Contact;
import com.organize4event.organize.models.ContactType;
import com.organize4event.organize.models.Institutional;
import com.organize4event.organize.services.InstitutionalService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstitutionalControler extends Controler {
    public InstitutionalControler(Context context) {
        super(context);
    }

    public void getInstitutional(String locale, final ControlResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getInstitutional(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonObject object = jsonObject.get("data").getAsJsonObject();
                        Institutional institutional = ApiClient.createGson().fromJson(object, Institutional.class);
                        listener.success(institutional);
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

    public void getContactType(String locale, final ControlResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getContactType(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<ContactType> contactTypes = (List<ContactType>) ApiClient.createGson().fromJson(array, new TypeToken<List<ContactType>>() {
                        }.getType());
                        listener.success(contactTypes);
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

    public void getContact(String locale, final ControlResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getContact(locale).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                Error error = parserError(jsonObject);
                if (error == null) {
                    if (jsonObject.get("data").isJsonNull()) {
                        listener.success(null);
                    } else {
                        JsonArray array = jsonObject.get("data").getAsJsonArray();
                        List<Contact> contacts = (List<Contact>) ApiClient.createGson().fromJson(array, new TypeToken<List<Contact>>() {
                        }.getType());
                        listener.success(contacts);
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
