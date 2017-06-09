package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FirstAccess implements Parcelable {

    public static final Parcelable.Creator<FirstAccess> CREATOR = new Parcelable.Creator<FirstAccess>() {
        @Override
        public FirstAccess createFromParcel(Parcel source) {
            return new FirstAccess(source);
        }

        @Override
        public FirstAccess[] newArray(int size) {
            return new FirstAccess[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("device_id")
    private String device_id;
    @SerializedName("instalation_date")
    private Date instalation_date;
    @SerializedName("locale")
    private String locale;

    public FirstAccess() {
    }

    protected FirstAccess(Parcel in) {
        this.id = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.device_id = in.readString();
        long tmpInstalation_date = in.readLong();
        this.instalation_date = tmpInstalation_date == -1 ? null : new Date(tmpInstalation_date);
        this.locale = in.readString();
    }

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

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Date getInstalation_date() {
        return instalation_date;
    }

    public void setInstalation_date(Date instalation_date) {
        this.instalation_date = instalation_date;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.device_id);
        dest.writeLong(this.instalation_date != null ? this.instalation_date.getTime() : -1);
        dest.writeString(this.locale);
    }
}
