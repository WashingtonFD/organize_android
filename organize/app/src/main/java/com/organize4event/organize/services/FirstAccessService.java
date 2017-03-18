package com.organize4event.organize.services;

import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.TermUse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface FirstAccessService {
    @GET("first_access/{device_id}")
    Call<FirstAccess> getFirstAccess(@Path("device_id") String device_id);

    @FormUrlEncoded
    @POST("first_access/save")
    Call<FirstAccess> saveFirstAccess(
            @Field("device_id") String device_id,
            @Field("locale") String locale,
            @Field("instalation_date") String instalation_date
    );

    @GET("access_platform/{locale}/{code_enum}")
    Call<AccessPlatform> getAccessPlatform(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @GET("term")
    Call<TermUse> getTermUse();
}
