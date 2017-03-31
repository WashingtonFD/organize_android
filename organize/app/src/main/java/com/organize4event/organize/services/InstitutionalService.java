package com.organize4event.organize.services;

import com.organize4event.organize.models.Contact;
import com.organize4event.organize.models.ContactType;
import com.organize4event.organize.models.Institutional;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface InstitutionalService {
    @GET("institutional/{locale}")
    Call<ArrayList<Institutional>> getInstitutional(@Path("locale") String locale);

    @GET("contact_type/{locale}")
    Call<ArrayList<ContactType>> getContactType(@Path("locale") String locale);

    @GET("contact/{locale}")
    Call<ArrayList<Contact>> getContact(@Path("locale") String locale);
}
