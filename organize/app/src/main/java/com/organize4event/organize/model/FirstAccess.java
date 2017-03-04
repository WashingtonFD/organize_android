package com.organize4event.organize.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FirstAccess extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("device_id")
    private String device_id;
    @SerializedName("instalation_date")
    private Date instalation_date;
    @SerializedName("locale")
    private String locale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.device_id);
        dest.writeLong(this.instalation_date != null ? this.instalation_date.getTime() : -1);
        dest.writeString(this.locale);
    }

    protected FirstAccess(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.device_id = in.readString();
        long tmpInstalation_date = in.readLong();
        this.instalation_date = tmpInstalation_date == -1 ? null : new Date(tmpInstalation_date);
        this.locale = in.readString();
    }

    public static final Creator<FirstAccess> CREATOR = new Creator<FirstAccess>() {
        @Override
        public FirstAccess createFromParcel(Parcel source) {
            return new FirstAccess(source);
        }

        @Override
        public FirstAccess[] newArray(int size) {
            return new FirstAccess[size];
        }
    };
}
