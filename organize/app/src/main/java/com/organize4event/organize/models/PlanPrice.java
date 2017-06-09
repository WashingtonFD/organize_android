package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlanPrice implements Parcelable {

    public static final Creator<PlanPrice> CREATOR = new Creator<PlanPrice>() {
        @Override
        public PlanPrice createFromParcel(Parcel source) {
            return new PlanPrice(source);
        }

        @Override
        public PlanPrice[] newArray(int size) {
            return new PlanPrice[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("code_enum")
    private int code_enum;
    @SerializedName("plan")
    private int plan;
    @SerializedName("price")
    private double price;

    public PlanPrice() {
    }

    protected PlanPrice(Parcel in) {
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.plan = in.readInt();
        this.price = in.readDouble();
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

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.locale);
        dest.writeInt(this.code_enum);
        dest.writeInt(this.plan);
        dest.writeDouble(this.price);
    }
}
