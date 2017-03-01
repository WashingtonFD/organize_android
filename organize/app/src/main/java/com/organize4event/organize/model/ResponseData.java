package com.organize4event.organize.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by marcelamelo on 28/02/17.
 */
@Parcel
public class ResponseData {
    @SerializedName("error")
    private String error;
    @SerializedName("success")
    private String success;

    public ResponseData() {
        super();
    }

    public boolean hasError()
    {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public String getSuccess() {
        return success;
    }
}
