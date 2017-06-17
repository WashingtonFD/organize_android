package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PlanService {

    @GET("plans/{locale}")
    Call<JsonObject> getPlan(@Path("locale") String locale);

    @GET("plan/{locale}/{code_enum}")
    Call<JsonObject> getPLanId(@Path("locale") String locale,
                               @Path("code_enum") int code_enum);

}
