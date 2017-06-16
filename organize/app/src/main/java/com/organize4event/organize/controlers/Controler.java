package com.organize4event.organize.controlers;

import android.content.Context;

import com.google.gson.JsonObject;
import com.organize4event.organize.R;


public class Controler {

    Context context;

    public Controler(Context context) {
        this.context = context;
    }

    protected Error parserError(String tag, JsonObject jsonObject) {
        Error error = null;
        if (jsonObject == null) {
            error = new Error(context.getString(R.string.error_message_generic));
        } else if (jsonObject.get("has_error").getAsBoolean()) {
            if (jsonObject.get("code").getAsInt() == 23000) {
                error = new Error(context.getString(R.string.error_message_data_violation));
            } else {
                String errorMessage = tag + ": " + jsonObject.get("message").getAsString();
                error = new Error(errorMessage);
            }
        }
        return error;
    }
}
