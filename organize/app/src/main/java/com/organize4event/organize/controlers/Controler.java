package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonObject;
import com.organize4event.organize.R;

public class Controler {

    Context context;

    public Controler(Context context) {
        this.context = context;
    }

    protected Error parserError(JsonObject jsonObject) {
        Error error = null;
        if (jsonObject.isJsonNull()) {
            error = new Error(context.getString(R.string.error_message_generic));
        } else if (jsonObject.get("has_error").getAsBoolean()) {
            error = new Error(jsonObject.get("message").getAsString());
        }
        return error;
    }
}
