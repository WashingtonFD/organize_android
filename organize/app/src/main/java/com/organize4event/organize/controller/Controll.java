package com.organize4event.organize.controller;

import android.content.Context;

import com.organize4event.organize.R;
import com.organize4event.organize.model.ResponseData;

public class Controll {

    Context context;

    public Controll(Context context) {
        this.context = context;
    }

    protected Error parserError(ResponseData responseData){
        Error error = null;
        if (responseData == null){
            error = new Error(context.getString(R.string.error_message_generic));
        }
        else if (responseData.hasError()){
            error = new Error(responseData.getError());
        }

        return error;
    }

}
