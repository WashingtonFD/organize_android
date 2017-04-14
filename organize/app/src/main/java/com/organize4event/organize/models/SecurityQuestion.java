package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class SecurityQuestion extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("locale")
    private String locale;
    @SerializedName("security_question")
    private String security_question;
    @SerializedName("private_use")
    private boolean private_use;

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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public boolean isPrivate_use() {
        return private_use;
    }

    public void setPrivate_use(boolean private_use) {
        this.private_use = private_use;
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
        dest.writeString(this.locale);
        dest.writeString(this.security_question);
        dest.writeByte(this.private_use ? (byte) 1 : (byte) 0);
    }

    public SecurityQuestion() {
        this.setIs_new(true);
    }

    protected SecurityQuestion(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.locale = in.readString();
        this.security_question = in.readString();
        this.private_use = in.readByte() != 0;
    }

    public static final Creator<SecurityQuestion> CREATOR = new Creator<SecurityQuestion>() {
        @Override
        public SecurityQuestion createFromParcel(Parcel source) {
            return new SecurityQuestion(source);
        }

        @Override
        public SecurityQuestion[] newArray(int size) {
            return new SecurityQuestion[size];
        }
    };
}
