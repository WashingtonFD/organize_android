package com.organize4event.organize.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User extends ErrorReturn{
    @SerializedName("id")
    private int id;
    @SerializedName("user_type")
    private UserType user_type;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("mail")
    private String mail;
    @SerializedName("password")
    private String password;
    @SerializedName("facebook_id")
    private String facebook_id;
    @SerializedName("linkedin_id")
    private String linkedin_id;
    @SerializedName("google_id")
    private String google_id;
    @SerializedName("profile_picture")
    private String profile_picture;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("rg_number")
    private String rg_number;
    @SerializedName("rg_emitter_uf")
    private String rg_emitter_uf;
    @SerializedName("rg_emitter_organ")
    private String rg_emitter_organ;
    @SerializedName("rg_emitter_date")
    private Date rg_emitter_date;
    @SerializedName("birth_date")
    private Date birth_date;
    @SerializedName("responsible_name")
    private String responsible_name;
    @SerializedName("responsible_cpf")
    private String responsible_cpf;
    @SerializedName("term")
    private TermUse term;
    @SerializedName("term_accept")
    private boolean term_accept;
    @SerializedName("term_accept_date")
    private Date term_accept_date;
    @SerializedName("plan")
    private Plan plan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserType user_type) {
        this.user_type = user_type;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getLinkedin_id() {
        return linkedin_id;
    }

    public void setLinkedin_id(String linkedin_id) {
        this.linkedin_id = linkedin_id;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg_number() {
        return rg_number;
    }

    public void setRg_number(String rg_number) {
        this.rg_number = rg_number;
    }

    public String getRg_emitter_uf() {
        return rg_emitter_uf;
    }

    public void setRg_emitter_uf(String rg_emitter_uf) {
        this.rg_emitter_uf = rg_emitter_uf;
    }

    public String getRg_emitter_organ() {
        return rg_emitter_organ;
    }

    public void setRg_emitter_organ(String rg_emitter_organ) {
        this.rg_emitter_organ = rg_emitter_organ;
    }

    public Date getRg_emitter_date() {
        return rg_emitter_date;
    }

    public void setRg_emitter_date(Date rg_emitter_date) {
        this.rg_emitter_date = rg_emitter_date;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getResponsible_name() {
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public String getResponsible_cpf() {
        return responsible_cpf;
    }

    public void setResponsible_cpf(String responsible_cpf) {
        this.responsible_cpf = responsible_cpf;
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeParcelable(this.user_type, flags);
        dest.writeString(this.full_name);
        dest.writeString(this.mail);
        dest.writeString(this.password);
        dest.writeString(this.facebook_id);
        dest.writeString(this.linkedin_id);
        dest.writeString(this.google_id);
        dest.writeString(this.profile_picture);
        dest.writeString(this.cpf);
        dest.writeString(this.rg_number);
        dest.writeString(this.rg_emitter_uf);
        dest.writeString(this.rg_emitter_organ);
        dest.writeLong(this.rg_emitter_date != null ? this.rg_emitter_date.getTime() : -1);
        dest.writeLong(this.birth_date != null ? this.birth_date.getTime() : -1);
        dest.writeString(this.responsible_name);
        dest.writeString(this.responsible_cpf);
        dest.writeParcelable(this.term, flags);
        dest.writeByte(this.term_accept ? (byte) 1 : (byte) 0);
        dest.writeLong(this.term_accept_date != null ? this.term_accept_date.getTime() : -1);
        dest.writeParcelable(this.plan, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user_type = in.readParcelable(UserType.class.getClassLoader());
        this.full_name = in.readString();
        this.mail = in.readString();
        this.password = in.readString();
        this.facebook_id = in.readString();
        this.linkedin_id = in.readString();
        this.google_id = in.readString();
        this.profile_picture = in.readString();
        this.cpf = in.readString();
        this.rg_number = in.readString();
        this.rg_emitter_uf = in.readString();
        this.rg_emitter_organ = in.readString();
        long tmpRg_emitter_date = in.readLong();
        this.rg_emitter_date = tmpRg_emitter_date == -1 ? null : new Date(tmpRg_emitter_date);
        long tmpBirth_date = in.readLong();
        this.birth_date = tmpBirth_date == -1 ? null : new Date(tmpBirth_date);
        this.responsible_name = in.readString();
        this.responsible_cpf = in.readString();
        this.term = in.readParcelable(TermUse.class.getClassLoader());
        this.term_accept = in.readByte() != 0;
        long tmpTerm_accept_date = in.readLong();
        this.term_accept_date = tmpTerm_accept_date == -1 ? null : new Date(tmpTerm_accept_date);
        this.plan = in.readParcelable(Plan.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
