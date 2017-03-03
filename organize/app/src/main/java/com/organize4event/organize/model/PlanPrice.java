package com.organize4event.organize.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PlanPrice extends ResponseData{
    @SerializedName("id")
    private int id;
    @SerializedName("plan")
    private int plan;
    @SerializedName("price")
    private double price;
    @SerializedName("is_active")
    private boolean is_active;
    @SerializedName("inserted_by")
    private int inserted_by;
    @SerializedName("updated_by")
    private int updated_by;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getInserted_by() {
        return inserted_by;
    }

    public void setInserted_by(int inserted_by) {
        this.inserted_by = inserted_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.plan);
        dest.writeDouble(this.price);
        dest.writeByte(this.is_active ? (byte) 1 : (byte) 0);
        dest.writeInt(this.inserted_by);
        dest.writeInt(this.updated_by);
    }

    protected PlanPrice(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.plan = in.readInt();
        this.price = in.readDouble();
        this.is_active = in.readByte() != 0;
        this.inserted_by = in.readInt();
        this.updated_by = in.readInt();
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
