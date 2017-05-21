package com.organize4event.organize.services;


import com.organize4event.organize.models.SecurityQuestion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserSecurityService {

    @GET("security_questions/{user_id}")
    Call<ArrayList<SecurityQuestion>> getSecurityQuestions(
            @Path("user_id") int user_id
    );
}
