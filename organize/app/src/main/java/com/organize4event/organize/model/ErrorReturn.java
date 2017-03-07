package com.organize4event.organize.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorReturn implements Parcelable {

    @SerializedName("sucess")
    private boolean sucess;
    @SerializedName("code")
    private int code;
    @SerializedName("error_return")
    private String error_return;
    @SerializedName("message")
    private String message;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_return() {
        return error_return;
    }

    public void setError_return(String error_return) {
        this.error_return = error_return;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.sucess ? (byte) 1 : (byte) 0);
        dest.writeInt(this.code);
        dest.writeString(this.error_return);
        dest.writeString(this.message);
    }

    public ErrorReturn() {
    }

    protected ErrorReturn(Parcel in) {
        this.sucess = in.readByte() != 0;
        this.code = in.readInt();
        this.error_return = in.readString();
        this.message = in.readString();
    }

}
