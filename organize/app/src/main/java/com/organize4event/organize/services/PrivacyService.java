package com.organize4event.organize.services;

import com.organize4event.organize.models.Privacy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PrivacyService {
    @GET("privacy/{locale}")
    Call<ArrayList<Privacy>> getPrivacy(@Path("locale") String locale);
}
