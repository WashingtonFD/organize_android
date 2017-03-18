package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.R;
import com.organize4event.organize.models.ErrorReturn;

public class Controll {

    Context context;

    public Controll(Context context) {
        this.context = context;
    }

    protected Error parserError(ErrorReturn errorReturn){
        Error error = null;
        if (errorReturn == null){
            error = new Error(context.getString(R.string.error_message_generic));
        }
        else if (errorReturn.isError()){
            error = new Error(errorReturn.getMessage());
        }
        return error;
    }
}
