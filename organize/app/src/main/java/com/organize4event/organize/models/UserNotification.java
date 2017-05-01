package com.organize4event.organize.models;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserNotification extends ErrorReturn implements Comparable<UserNotification>{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("brief_description")
    private String brief_description;
    @SerializedName("description")
    private String description;
    @SerializedName("notification_date")
    private Date notification_date;
    @SerializedName("is_read")
    private boolean is_read;

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

    public String getBrief_description() {
        return brief_description;
    }

    public void setBrief_description(String brief_description) {
        this.brief_description = brief_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(Date notification_date) {
        this.notification_date = notification_date;
    }

    public boolean is_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
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
        dest.writeString(this.brief_description);
        dest.writeString(this.description);
        dest.writeLong(this.notification_date != null ? this.notification_date.getTime() : -1);
        dest.writeByte(this.is_read ? (byte) 1 : (byte) 0);
    }

    public UserNotification() {
    }

    protected UserNotification(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readInt();
        this.brief_description = in.readString();
        this.description = in.readString();
        long tmpNotification_date = in.readLong();
        this.notification_date = tmpNotification_date == -1 ? null : new Date(tmpNotification_date);
        this.is_read = in.readByte() != 0;
    }

    public static final Creator<UserNotification> CREATOR = new Creator<UserNotification>() {
        @Override
        public UserNotification createFromParcel(Parcel source) {
            return new UserNotification(source);
        }

        @Override
        public UserNotification[] newArray(int size) {
            return new UserNotification[size];
        }
    };

    @Override
    public int compareTo(@NonNull UserNotification o) {
        if (this.getNotification_date().getTime() < o.getNotification_date().getTime()){
            return 1;
        }
        else if (this.getNotification_date().getTime() > o.getNotification_date().getTime()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
