package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class TermUse implements Parcelable {

    public static final Parcelable.Creator<TermUse> CREATOR = new Parcelable.Creator<TermUse>() {
        @Override
        public TermUse createFromParcel(Parcel source) {
            return new TermUse(source);
        }

        @Override
        public TermUse[] newArray(int size) {
            return new TermUse[size];
        }
    };
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

    public TermUse() {
    }

    protected TermUse(Parcel in) {
        this.id = in.readInt();
        this.locale = in.readString();
        this.version_name = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        long tmpPublication_date = in.readLong();
        this.publication_date = tmpPublication_date == -1 ? null : new Date(tmpPublication_date);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.locale);
        dest.writeString(this.version_name);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.publication_date != null ? this.publication_date.getTime() : -1);
    }
}
