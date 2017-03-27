package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PlanPrice extends ErrorReturn{
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
        dest.writeInt(this.plan);
        dest.writeDouble(this.price);
        dest.writeByte(this.is_active ? (byte) 1 : (byte) 0);
    }

    public PlanPrice() {
        this.setIs_new(true);
    }

    protected PlanPrice(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.plan = in.readInt();
        this.price = in.readDouble();
        this.is_active = in.readByte() != 0;
    }

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
}
