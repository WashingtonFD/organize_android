package com.organize4event.organize.services;

import com.organize4event.organize.models.Privacy;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface PrivacyService {
    @GET("privacy/{locale}")
    Call<ArrayList<Privacy>> getPrivacy(@Path("locale") String locale);
}
