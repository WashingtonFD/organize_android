package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PlanAdvantage extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("code_enum")
    private int code_enum;
    @SerializedName("plan")
    private int plan;
    @SerializedName("advantage")
    private String advantage;

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

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
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
        dest.writeInt(this.plan);
        dest.writeString(this.advantage);
    }

    public PlanAdvantage() {
    }

    protected PlanAdvantage(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.plan = in.readInt();
        this.advantage = in.readString();
    }

    public static final Creator<PlanAdvantage> CREATOR = new Creator<PlanAdvantage>() {
        @Override
        public PlanAdvantage createFromParcel(Parcel source) {
            return new PlanAdvantage(source);
        }

        @Override
        public PlanAdvantage[] newArray(int size) {
            return new PlanAdvantage[size];
        }
    };
}
