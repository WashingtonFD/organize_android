package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorReturn implements Parcelable {

    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("exception")
    private String exception;
    @SerializedName("data")
    private Object data;

    public boolean isError() {

        return error;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public Object getData() {
        return data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeString(this.exception);
    }

    public ErrorReturn() {
    }

    protected ErrorReturn(Parcel in) {
        this.error = in.readByte() != 0;
        this.code = in.readInt();
        this.message = in.readString();
        this.exception = in.readString();
        this.data = in.readParcelable(Object.class.getClassLoader());
    }

}
