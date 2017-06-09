package com.organize4event.organize.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface TermUseService {
    @GET("term")
    Call<JsonObject> getTermUse();

    @FormUrlEncoded
    @POST("user_term/accept")
    Call<JsonObject> saveUserTerm(
            @Field("user_id") int user_id,
            @Field("term_id") int term_id,
            @Field("term_accept") int term_accept,
            @Field("term_accept_date") String term_accept_date
    );
}
