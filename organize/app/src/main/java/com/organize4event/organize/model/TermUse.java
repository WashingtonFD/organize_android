package com.organize4event.organize.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class TermUse extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("version_name")
    private String version_name;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("publication_date")
    private Date publication_date;
    @SerializedName("is_active")
    private boolean is_active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.locale);
        dest.writeString(this.version_name);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.publication_date != null ? this.publication_date.getTime() : -1);
        dest.writeByte(this.is_active ? (byte) 1 : (byte) 0);
    }

    protected TermUse(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.locale = in.readString();
        this.version_name = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        long tmpPublication_date = in.readLong();
        this.publication_date = tmpPublication_date == -1 ? null : new Date(tmpPublication_date);
        this.is_active = in.readByte() != 0;
    }

    public static final Creator<TermUse> CREATOR = new Creator<TermUse>() {
        @Override
        public TermUse createFromParcel(Parcel source) {
            return new TermUse(source);
        }

        @Override
        public TermUse[] newArray(int size) {
            return new TermUse[size];
        }
    };
}
