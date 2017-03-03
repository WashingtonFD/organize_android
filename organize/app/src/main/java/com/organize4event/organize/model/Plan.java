package com.organize4event.organize.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Plan extends ResponseData{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("security_code")
    private int security_code;
    @SerializedName("advantages")
    private ArrayList<String> advantages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(int security_code) {
        this.security_code = security_code;
    }

    public ArrayList<String> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(ArrayList<String> advantages) {
        this.advantages = advantages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.security_code);
        dest.writeStringList(this.advantages);
    }

    protected Plan(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.security_code = in.readInt();
        this.advantages = in.createStringArrayList();
    }

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
}
