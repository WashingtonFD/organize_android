package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.Contact;
import com.organize4event.organize.models.ContactType;
import com.organize4event.organize.models.Institutional;
import com.organize4event.organize.services.InstitutionalService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstitutionalControler extends Controler {
    public InstitutionalControler(Context context) {
        super(context);
    }

    public void getInstitutional(String locale, final ControllResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getInstitutional(locale).enqueue(new Callback<Institutional>() {
            @Override
            public void onResponse(Call<Institutional> call, Response<Institutional> response) {
                Institutional institutional = (Institutional) response.body();
                Error error = parserError(institutional);
                if (error == null) {
                    listener.success(institutional);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<Institutional> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void getContactType(String locale, final ControllResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getContactType(locale).enqueue(new Callback<ArrayList<ContactType>>() {
            @Override
            public void onResponse(Call<ArrayList<ContactType>> call, Response<ArrayList<ContactType>> response) {
                ArrayList<ContactType> contactTypes = (ArrayList<ContactType>) response.body();
                Error error = parserError(contactTypes.get(0));
                if (error == null) {
                    listener.success(contactTypes);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContactType>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void getContact(String locale, final ControllResponseListener listener) {
        InstitutionalService service = ApiClient.getRetrofit().create(InstitutionalService.class);
        service.getContact(locale).enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                ArrayList<Contact> contacts = (ArrayList<Contact>) response.body();
                Error error = parserError(contacts.get(0));
                if (error == null) {
                    listener.success(contacts);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
