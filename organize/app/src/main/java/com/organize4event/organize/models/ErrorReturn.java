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
    @SerializedName("is_new")
    private boolean is_new;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean is_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
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
        dest.writeByte(this.is_new ? (byte) 1 : (byte) 0);
    }

    public ErrorReturn() {
        this.setIs_new(false);
    }

    protected ErrorReturn(Parcel in) {
        this.error = in.readByte() != 0;
        this.code = in.readInt();
        this.message = in.readString();
        this.exception = in.readString();
        this.is_new = in.readByte() != 0;
    }

}
