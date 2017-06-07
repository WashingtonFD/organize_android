package com.organize4event.organize.services;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserSecurityService {

    @GET("security_questions/{user_id}")
    Call<JsonObject> getSecurityQuestions(
            @Path("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("password_recovery")
    Call<JsonObject> sendMail(@Field("mail") String mail,
                              @Field("user_security_id") int user_security_id,
                              @Field("user_anwser") String user_anwser);
}
