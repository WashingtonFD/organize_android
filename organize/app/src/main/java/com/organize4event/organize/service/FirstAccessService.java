package com.organize4event.organize.service;

import com.organize4event.organize.model.TermUse;

import retrofit.Call;
import retrofit.http.GET;

public interface FirstAccessService {
    @GET("term")
    Call<TermUse> getTermUse();
}
