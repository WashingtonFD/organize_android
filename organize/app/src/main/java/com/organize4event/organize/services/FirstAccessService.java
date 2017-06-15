package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface FirstAccessService {
    @GET("first_access/{device_id}")
    Call<JsonObject> getFirstAccess(@Path("device_id") String device_id);

    @FormUrlEncoded
    @POST("first_access/save")
    Call<JsonObject> saveFirstAccess(
            @Field("user_id") int user_id,
            @Field("device_id") String device_id,
            @Field("locale") String locale,
            @Field("instalation_date") String instalation_date
    );

    @GET("access_platform/{locale}/{code_enum}")
    Call<JsonObject> getAccessPlatform(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @FormUrlEncoded
    @POST("first_access/{first_acess_id}/edit")
    Call<JsonObject> updateLocale(@Path("first_acess_id") int first_acess_id,
                                  @Field("locale") String locale);

    @FormUrlEncoded
    @POST("first_access/{first_acess_id}")
    Call<JsonObject> updateUserFirstAccess(@Path("first_acess_id") int first_acess_id,
                                           @Field("user") int user_id);
}
