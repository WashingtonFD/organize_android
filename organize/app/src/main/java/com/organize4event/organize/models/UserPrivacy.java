package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class UserPrivacy extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("privacy")
    private Privacy privacy;
    @SerializedName("checking")
    private boolean checking;

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

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.user);
        dest.writeParcelable(this.privacy, flags);
        dest.writeByte(this.checking ? (byte) 1 : (byte) 0);
    }

    public UserPrivacy() {
    }

    protected UserPrivacy(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readInt();
        this.privacy = in.readParcelable(Privacy.class.getClassLoader());
        this.checking = in.readByte() != 0;
    }

    public static final Creator<UserPrivacy> CREATOR = new Creator<UserPrivacy>() {
        @Override
        public UserPrivacy createFromParcel(Parcel source) {
            return new UserPrivacy(source);
        }

        @Override
        public UserPrivacy[] newArray(int size) {
            return new UserPrivacy[size];
        }
    };
}
