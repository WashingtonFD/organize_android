package com.organize4event.organize.controlers;

import android.content.Context;

import com.organize4event.organize.R;

public class Controler {

    Context context;

    public Controler(Context context) {
        this.context = context;
    }

    protected Error parserError(ErrorReturn errorReturn) {
        Error error = null;
        if (errorReturn == null) {
            error = new Error(context.getString(R.string.error_message_generic));
        } else if (errorReturn.isError()) {
            error = new Error(errorReturn.getMessage());
        }
        return error;
    }
}
