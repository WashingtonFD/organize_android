package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.TermUse;
import com.organize4event.organize.models.UserTerm;
import com.organize4event.organize.services.TermUseService;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermUseControll extends Controll {
    public TermUseControll(Context context) {
        super(context);
    }

    public void getTermUse(final ControllResponseListener listener) {
        TermUseService service = ApiClient.getRetrofit().create(TermUseService.class);
        service.getTermUse().enqueue(new Callback<TermUse>() {
            @Override
            public void onResponse(Call<TermUse> call, Response<TermUse> response) {
                TermUse termUse = (TermUse) response.body();
                Error error = parserError(termUse);
                if (error == null) {
                    listener.success(termUse);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<TermUse> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }

    public void saveUserTerm(UserTerm userTerm, int term_accept, final ControllResponseListener listener) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        TermUseService service = ApiClient.getRetrofit().create(TermUseService.class);
        service.saveUserTerm(userTerm.getUser(),
                userTerm.getTerm().getId(),
                term_accept,
                fullDateFormat.format(userTerm.getTerm_accept_date())).enqueue(new Callback<UserTerm>() {
            @Override
            public void onResponse(Call<UserTerm> call, Response<UserTerm> response) {
                UserTerm userTerm = (UserTerm) response.body();
                Error error = parserError(userTerm);
                if (error == null) {
                    listener.success(userTerm);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<UserTerm> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
