package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Plan implements Parcelable {

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
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
    @SerializedName("security_code")
    private String security_code;
    @SerializedName("advantages")
    private ArrayList<PlanAdvantage> advantages;
    @SerializedName("price")
    private ArrayList<PlanPrice> price;

    public Plan() {
    }

    protected Plan(Parcel in) {
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.security_code = in.readString();
        this.advantages = in.createTypedArrayList(PlanAdvantage.CREATOR);
        this.price = in.createTypedArrayList(PlanPrice.CREATOR);
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

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public ArrayList<PlanAdvantage> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(ArrayList<PlanAdvantage> advantages) {
        this.advantages = advantages;
    }

    public ArrayList<PlanPrice> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<PlanPrice> price) {
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.security_code);
        dest.writeTypedList(this.advantages);
        dest.writeTypedList(this.price);
    }
}
