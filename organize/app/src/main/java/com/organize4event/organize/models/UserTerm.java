package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserTerm extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("term")
    private TermUse term;
    @SerializedName("term_accept")
    private boolean term_accept;
    @SerializedName("term_accept_date")
    private Date term_accept_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public TermUse getTerm() {
        return term;
    }

    public void setTerm(TermUse term) {
        this.term = term;
    }

    public boolean isTerm_accept() {
        return term_accept;
    }

    public void setTerm_accept(boolean term_accept) {
        this.term_accept = term_accept;
    }

    public Date getTerm_accept_date() {
        return term_accept_date;
    }

    public void setTerm_accept_date(Date term_accept_date) {
        this.term_accept_date = term_accept_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.user);
        dest.writeParcelable(this.term, flags);
        dest.writeByte(this.term_accept ? (byte) 1 : (byte) 0);
        dest.writeLong(this.term_accept_date != null ? this.term_accept_date.getTime() : -1);
    }

    public UserTerm() {
        this.setIs_new(true);
    }

    protected UserTerm(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user = in.readInt();
        this.term = in.readParcelable(TermUse.class.getClassLoader());
        this.term_accept = in.readByte() != 0;
        long tmpTerm_accept_date = in.readLong();
        this.term_accept_date = tmpTerm_accept_date == -1 ? null : new Date(tmpTerm_accept_date);
    }

    public static final Creator<UserTerm> CREATOR = new Creator<UserTerm>() {
        @Override
        public UserTerm createFromParcel(Parcel source) {
            return new UserTerm(source);
        }

        @Override
        public UserTerm[] newArray(int size) {
            return new UserTerm[size];
        }
    };
}
