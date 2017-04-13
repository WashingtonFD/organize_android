package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Token extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("login_type")
    private LoginType login_type;
    @SerializedName("access_platform")
    private AccessPlatform access_platform;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("access_date")
    private Date access_date;
    @SerializedName("keep_logged")
    private boolean keep_logged;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginType getLogin_type() {
        return login_type;
    }

    public void setLogin_type(LoginType login_type) {
        this.login_type = login_type;
    }

    public AccessPlatform getAccess_platform() {
        return access_platform;
    }

    public void setAccess_platform(AccessPlatform access_platform) {
        this.access_platform = access_platform;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getAccess_date() {
        return access_date;
    }

    public void setAccess_date(Date access_date) {
        this.access_date = access_date;
    }

    public boolean isKeep_logged() {
        return keep_logged;
    }

    public void setKeep_logged(boolean keep_logged) {
        this.keep_logged = keep_logged;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeParcelable(this.login_type, flags);
        dest.writeParcelable(this.access_platform, flags);
        dest.writeString(this.access_token);
        dest.writeLong(this.access_date != null ? this.access_date.getTime() : -1);
        dest.writeByte(this.keep_logged ? (byte) 1 : (byte) 0);
    }

    public Token() {
        this.setIs_new(true);
    }

    protected Token(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.login_type = in.readParcelable(LoginType.class.getClassLoader());
        this.access_platform = in.readParcelable(AccessPlatform.class.getClassLoader());
        this.access_token = in.readString();
        long tmpAccess_date = in.readLong();
        this.access_date = tmpAccess_date == -1 ? null : new Date(tmpAccess_date);
        this.keep_logged = in.readByte() != 0;
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
