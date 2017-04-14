package com.organize4event.organize.services;

import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.models.UserPrivacy;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PrivacyService {
    @GET("privacy/{locale}")
    Call<ArrayList<Privacy>> getPrivacy(@Path("locale") String locale);

    @FormUrlEncoded
    @POST("user_privacy/save")
    Call<UserPrivacy> saveUserPrivacy(
            @Field("user") int user_id,
            @Field("privacy") int privacy_id,
            @Field("checking") int checking
    );
}
