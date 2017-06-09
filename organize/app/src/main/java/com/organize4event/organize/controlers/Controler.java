package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.Constants;

public class Controler {

    Context context;

    public Controler(Context context) {
        this.context = context;
    }

    protected Error parserError(JsonObject jsonObject) {
        Error error = null;
        if (jsonObject == null) {
            error = new Error(context.getString(R.string.error_message_generic));
        } else if (jsonObject.get("has_error").getAsBoolean()) {
            error = new Error(jsonObject.get("message").getAsString());
        }
        return error;
    }

    protected Gson createGson(){
        Gson gson  = new GsonBuilder().setDateFormat(Constants.FULL_DATE_FORMAT).create();
        return gson;
    }
}
