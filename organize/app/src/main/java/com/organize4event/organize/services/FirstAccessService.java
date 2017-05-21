package com.organize4event.organize.services;

import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface FirstAccessService {
    @GET("first_access/{device_id}")
    Call<FirstAccess> getFirstAccess(@Path("device_id") String device_id);

    @FormUrlEncoded
    @POST("first_access/save")
    Call<FirstAccess> saveFirstAccess(
            @Field("user_id") int user_id,
            @Field("device_id") String device_id,
            @Field("locale") String locale,
            @Field("instalation_date") String instalation_date
    );

    @GET("access_platform/{locale}/{code_enum}")
    Call<AccessPlatform> getAccessPlatform(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);
}
