package com.organize4event.organize.services;

import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserService {

    @GET("user_type/{locale}/{code_enum}")
    Call<UserType> getUserType(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @FormUrlEncoded
    @POST("user/save")
    Call<User> saveUser(
            @Field("user_type") int user_type_id,
            @Field("plan") int plan_id,
            @Field("full_name") String full_name,
            @Field("mail") String mail,
            @Field("password") String password,
            @Field("cpf") String cpf,
            @Field("birth_date") String birth_date,
            @Field("gender") String gender
    );
}
