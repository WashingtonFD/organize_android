package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class UserSetting extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("settings")
    private Setting settings;
    @SerializedName("checking")
    private boolean checking;
    @SerializedName("value")
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.settings, flags);
        dest.writeByte(this.checking ? (byte) 1 : (byte) 0);
        dest.writeInt(this.value);
    }

    public UserSetting() {
        this.setIs_new(true);
    }

    protected UserSetting(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.settings = in.readParcelable(Setting.class.getClassLoader());
        this.checking = in.readByte() != 0;
        this.value = in.readInt();
    }

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
}
