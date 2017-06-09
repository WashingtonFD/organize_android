package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserSecurity implements Parcelable {

    public static final Parcelable.Creator<UserSecurity> CREATOR = new Parcelable.Creator<UserSecurity>() {
        @Override
        public UserSecurity createFromParcel(Parcel source) {
            return new UserSecurity(source);
        }

        @Override
        public UserSecurity[] newArray(int size) {
            return new UserSecurity[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("security_question")
    private SecurityQuestion security_question;
    @SerializedName("access_platform")
    private AccessPlatform access_platform;
    @SerializedName("security_answer")
    private String security_answer;
    @SerializedName("last_update_date")
    private Date last_update_date;
    @SerializedName("last_update_identifier")
    private String last_update_identifier;

    public UserSecurity() {
    }

    protected UserSecurity(Parcel in) {
        this.id = in.readInt();
        this.user = in.readInt();
        this.security_question = in.readParcelable(SecurityQuestion.class.getClassLoader());
        this.access_platform = in.readParcelable(AccessPlatform.class.getClassLoader());
        this.security_answer = in.readString();
        long tmpLast_update_date = in.readLong();
        this.last_update_date = tmpLast_update_date == -1 ? null : new Date(tmpLast_update_date);
        this.last_update_identifier = in.readString();
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

    public SecurityQuestion getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(SecurityQuestion security_question) {
        this.security_question = security_question;
    }

    public AccessPlatform getAccess_platform() {
        return access_platform;
    }

    public void setAccess_platform(AccessPlatform access_platform) {
        this.access_platform = access_platform;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    public Date getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(Date last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getLast_update_identifier() {
        return last_update_identifier;
    }

    public void setLast_update_identifier(String last_update_identifier) {
        this.last_update_identifier = last_update_identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user);
        dest.writeParcelable(this.security_question, flags);
        dest.writeParcelable(this.access_platform, flags);
        dest.writeString(this.security_answer);
        dest.writeLong(this.last_update_date != null ? this.last_update_date.getTime() : -1);
        dest.writeString(this.last_update_identifier);
    }
}
