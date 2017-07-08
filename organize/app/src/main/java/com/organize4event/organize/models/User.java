package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class User implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("user_type")
    private UserType user_type;
    @SerializedName("token")
    private Token token;
    @SerializedName("plan")
    private Plan plan;
    @SerializedName("privacy")
    private Privacy privacy;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("mail")
    private String mail;
    @SerializedName("password")
    private String password;
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
    private String rg_emitter_date;
    @SerializedName("birth_date")
    private Date birth_date;
    @SerializedName("gender")
    private String gender;
    @SerializedName("responsible_name")
    private String responsible_name;
    @SerializedName("responsible_cpf")
    private String responsible_cpf;
    @SerializedName("user_term")
    private UserTerm user_term;
    @SerializedName("user_validate")
    private UserValidate user_validate;
    @SerializedName("user_security")
    private UserSecurity user_security;
    @SerializedName("user_settings")
    private ArrayList<UserSetting> user_settings;
    @SerializedName("user_notifications")
    private ArrayList<UserNotification> user_notifications;

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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
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

    public String getRg_emitter_date() {
        return rg_emitter_date;
    }

    public void setRg_emitter_date(String rg_emitter_date) {
        this.rg_emitter_date = rg_emitter_date;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public UserTerm getUser_term() {
        return user_term;
    }

    public void setUser_term(UserTerm user_term) {
        this.user_term = user_term;
    }

    public UserValidate getUser_validate() {
        return user_validate;
    }

    public void setUser_validate(UserValidate user_validate) {
        this.user_validate = user_validate;
    }

    public UserSecurity getUser_security() {
        return user_security;
    }

    public void setUser_security(UserSecurity user_security) {
        this.user_security = user_security;
    }

    public ArrayList<UserSetting> getUser_settings() {
        return user_settings;
    }

    public void setUser_settings(ArrayList<UserSetting> user_settings) {
        this.user_settings = user_settings;
    }

    public ArrayList<UserNotification> getUser_notifications() {
        return user_notifications;
    }

    public void setUser_notifications(ArrayList<UserNotification> user_notifications) {
        this.user_notifications = user_notifications;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.user_type, flags);
        dest.writeParcelable(this.token, flags);
        dest.writeParcelable(this.plan, flags);
        dest.writeParcelable(this.privacy, flags);
        dest.writeString(this.full_name);
        dest.writeString(this.mail);
        dest.writeString(this.password);
        dest.writeString(this.profile_picture);
        dest.writeString(this.cpf);
        dest.writeString(this.rg_number);
        dest.writeString(this.rg_emitter_uf);
        dest.writeString(this.rg_emitter_organ);
        dest.writeString(this.rg_emitter_date);
        dest.writeLong(this.birth_date != null ? this.birth_date.getTime() : -1);
        dest.writeString(this.gender);
        dest.writeString(this.responsible_name);
        dest.writeString(this.responsible_cpf);
        dest.writeParcelable(this.user_term, flags);
        dest.writeParcelable(this.user_validate, flags);
        dest.writeParcelable(this.user_security, flags);
        dest.writeTypedList(this.user_settings);
        dest.writeTypedList(this.user_notifications);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.user_type = in.readParcelable(UserType.class.getClassLoader());
        this.token = in.readParcelable(Token.class.getClassLoader());
        this.plan = in.readParcelable(Plan.class.getClassLoader());
        this.privacy = in.readParcelable(Privacy.class.getClassLoader());
        this.full_name = in.readString();
        this.mail = in.readString();
        this.password = in.readString();
        this.profile_picture = in.readString();
        this.cpf = in.readString();
        this.rg_number = in.readString();
        this.rg_emitter_uf = in.readString();
        this.rg_emitter_organ = in.readString();
        this.rg_emitter_date = in.readString();
        long tmpBirth_date = in.readLong();
        this.birth_date = tmpBirth_date == -1 ? null : new Date(tmpBirth_date);
        this.gender = in.readString();
        this.responsible_name = in.readString();
        this.responsible_cpf = in.readString();
        this.user_term = in.readParcelable(UserTerm.class.getClassLoader());
        this.user_validate = in.readParcelable(UserValidate.class.getClassLoader());
        this.user_security = in.readParcelable(UserSecurity.class.getClassLoader());
        this.user_settings = in.createTypedArrayList(UserSetting.CREATOR);
        this.user_notifications = in.createTypedArrayList(UserNotification.CREATOR);
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
