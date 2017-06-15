package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface InstitutionalService {
    @GET("institutional/{locale}")
    Call<JsonObject> getInstitutional(@Path("locale") String locale);

    @GET("contact_types/{locale}")
    Call<JsonObject> getContactType(@Path("locale") String locale);

    @GET("contacts/{locale}")
    Call<JsonObject> getContact(@Path("locale") String locale);
}
