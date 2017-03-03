package com.organize4event.organize.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResponseData implements Parcelable{
    @SerializedName("error")
    private String error;
    @SerializedName("success")
    private String success;

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

    protected ResponseData(Parcel in) {
        error = in.readString();
        success = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeString(success);
    }

}
