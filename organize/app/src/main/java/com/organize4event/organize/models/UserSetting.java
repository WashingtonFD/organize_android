package com.organize4event.organize.models;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UserSetting extends ErrorReturn implements Comparable<UserSetting> {
    public static final Creator<UserSetting> CREATOR = new Creator<UserSetting>() {
        @Override
        public UserSetting createFromParcel(Parcel source) {
            return new UserSetting(source);
        }

        @Override
        public UserSetting[] newArray(int size) {
            return new UserSetting[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("setting")
    private Setting setting;
    @SerializedName("checking")
    private boolean checking;
    @SerializedName("value")
    private int value;

    public UserSetting() {
    }

    protected UserSetting(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readInt();
        this.setting = in.readParcelable(Setting.class.getClassLoader());
        this.checking = in.readByte() != 0;
        this.value = in.readInt();
    }

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

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NonNull UserSetting o) {
        if (this.getSetting().getCode_enum() > o.getSetting().getCode_enum()) {
            return 1;
        }
        if (this.getSetting().getCode_enum() < o.getSetting().getCode_enum()) {
            return -1;
        }
        return 0;
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
        dest.writeParcelable(this.setting, flags);
        dest.writeByte(this.checking ? (byte) 1 : (byte) 0);
        dest.writeInt(this.value);
    }
}
