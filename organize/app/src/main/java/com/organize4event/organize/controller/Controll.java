package com.organize4event.organize.controller;

import android.content.Context;

import com.organize4event.organize.R;
import com.organize4event.organize.model.ErrorReturn;

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
        else if (!errorReturn.isSucess()){
            error = new Error(errorReturn.getError_return());
        }
        return error;
    }
}
