package com.organize4event.organize.services;

import com.organize4event.organize.models.TermUse;
import com.organize4event.organize.models.UserTerm;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface TermUseService {
    @GET("term")
    Call<TermUse> getTermUse();

    @FormUrlEncoded
    @POST("user_term/accept")
    Call<UserTerm>saveUserTerm(
            @Field("user_id") int user_id,
            @Field("term_id") int term_id,
            @Field("term_accept") int term_accept,
            @Field("term_accept_date") String term_accept_date
    );
}
