package com.organize4event.organize.services;

import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TokenService {

    @GET("login_type/{locale}/{code_enum}")
    Call<LoginType> getLoginType(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @GET("token/{first_access_id}")
    Call<Token> getToken(@Path("first_access_id") int first_access_id);
}
