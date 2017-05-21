package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Institutional extends ErrorReturn {
    public static final Creator<Institutional> CREATOR = new Creator<Institutional>() {
        @Override
        public Institutional createFromParcel(Parcel source) {
            return new Institutional(source);
        }

        @Override
        public Institutional[] newArray(int size) {
            return new Institutional[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("code_enum")
    private int code_enum;
    @SerializedName("site_url")
    private String site_url;
    @SerializedName("description")
    private String description;
    @SerializedName("mission")
    private String mission;
    @SerializedName("vision")
    private String vision;
    @SerializedName("values")
    private String values;
    @SerializedName("is_active")
    private boolean is_active;

    public Institutional() {
        this.setIs_new(true);
    }

    protected Institutional(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.site_url = in.readString();
        this.description = in.readString();
        this.mission = in.readString();
        this.vision = in.readString();
        this.values = in.readString();
        this.is_active = in.readByte() != 0;
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

    public int getCode_enum() {
        return code_enum;
    }

    public void setCode_enum(int code_enum) {
        this.code_enum = code_enum;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
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
        dest.writeInt(this.code_enum);
        dest.writeString(this.site_url);
        dest.writeString(this.description);
        dest.writeString(this.mission);
        dest.writeString(this.vision);
        dest.writeString(this.values);
        dest.writeByte(this.is_active ? (byte) 1 : (byte) 0);
    }
}
