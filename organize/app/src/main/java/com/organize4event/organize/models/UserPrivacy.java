package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class UserPrivacy extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("privacy")
    private Privacy privacy;
    @SerializedName("cheking")
    private boolean cheking;

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

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public boolean isCheking() {
        return cheking;
    }

    public void setCheking(boolean cheking) {
        this.cheking = cheking;
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
        dest.writeParcelable(this.privacy, flags);
        dest.writeByte(this.cheking ? (byte) 1 : (byte) 0);
    }

    public UserPrivacy() {
        this.setIs_new(true);
    }

    protected UserPrivacy(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.privacy = in.readParcelable(Privacy.class.getClassLoader());
        this.cheking = in.readByte() != 0;
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
