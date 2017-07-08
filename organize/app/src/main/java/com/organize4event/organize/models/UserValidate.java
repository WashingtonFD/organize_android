package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserValidate implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("send_mail_date")
    private Date send_mail_date;
    @SerializedName("token_validate")
    private String token_validate;
    @SerializedName("is_valid")
    private boolean is_valid;
    @SerializedName("validate_date")
    private Date validate_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getSend_mail_date() {
        return send_mail_date;
    }

    public void setSend_mail_date(Date send_mail_date) {
        this.send_mail_date = send_mail_date;
    }

    public String getToken_validate() {
        return token_validate;
    }

    public void setToken_validate(String token_validate) {
        this.token_validate = token_validate;
    }

    public boolean is_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Date getValidate_date() {
        return validate_date;
    }

    public void setValidate_date(Date validate_date) {
        this.validate_date = validate_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user);
        dest.writeLong(this.send_mail_date != null ? this.send_mail_date.getTime() : -1);
        dest.writeString(this.token_validate);
        dest.writeByte(this.is_valid ? (byte) 1 : (byte) 0);
        dest.writeLong(this.validate_date != null ? this.validate_date.getTime() : -1);
    }

    public UserValidate() {
    }

    protected UserValidate(Parcel in) {
        this.id = in.readInt();
        this.user = in.readInt();
        long tmpSend_mail_date = in.readLong();
        this.send_mail_date = tmpSend_mail_date == -1 ? null : new Date(tmpSend_mail_date);
        this.token_validate = in.readString();
        this.is_valid = in.readByte() != 0;
        long tmpValidate_date = in.readLong();
        this.validate_date = tmpValidate_date == -1 ? null : new Date(tmpValidate_date);
    }

    public static final Parcelable.Creator<UserValidate> CREATOR = new Parcelable.Creator<UserValidate>() {
        @Override
        public UserValidate createFromParcel(Parcel source) {
            return new UserValidate(source);
        }

        @Override
        public UserValidate[] newArray(int size) {
            return new UserValidate[size];
        }
    };
}
