package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Setting extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("code_enum")
    private int code_enum;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("check_default")
    private boolean check_default;

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

    public int getCode_enum() {
        return code_enum;
    }

    public void setCode_enum(int code_enum) {
        this.code_enum = code_enum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheck_default() {
        return check_default;
    }

    public void setCheck_default(boolean check_default) {
        this.check_default = check_default;
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
        dest.writeInt(this.code_enum);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeByte(this.check_default ? (byte) 1 : (byte) 0);
    }

    public Setting() {
        this.setIs_new(true);
    }

    protected Setting(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.check_default = in.readByte() != 0;
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        @Override
        public Setting createFromParcel(Parcel source) {
            return new Setting(source);
        }

        @Override
        public Setting[] newArray(int size) {
            return new Setting[size];
        }
    };
}
