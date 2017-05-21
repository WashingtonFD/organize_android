package com.organize4event.organize.services;

import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


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
