package com.organize4event.organize.services;

import com.organize4event.organize.models.UserType;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UserService {

    @GET("user_type/{locale}/{code_enum}")
    Call<UserType> getUserType(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);
}
