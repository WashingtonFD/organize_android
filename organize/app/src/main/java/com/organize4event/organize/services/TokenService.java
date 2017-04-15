package com.organize4event.organize.services;

import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface TokenService {

    @GET("login_type/{locale}/{code_enum}")
    Call<LoginType> getLoginType(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(
            @Field("mail") String mail,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("token/save")
    Call<Token> saveToken(
            @Field("user_id") int user_id,
            @Field("login_type") int login_type_id,
            @Field("access_platform") int access_platform_id,
            @Field("access_date") String access_date,
            @Field("keep_logged") int keep_logged
    );
}
