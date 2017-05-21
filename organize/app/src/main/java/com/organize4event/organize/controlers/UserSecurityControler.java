package com.organize4event.organize.controlers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.SecurityQuestion;
import com.organize4event.organize.services.UserSecurityService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserSecurityControler extends Controler {
    public UserSecurityControler(Context context) {
        super(context);
    }

    public void getSecurityQuestions(int user_id, final ControlResponseListener listener) {
        UserSecurityService service = ApiClient.getRetrofit().create(UserSecurityService.class);
        service.getSecurityQuestions(user_id).enqueue(new Callback<ArrayList<SecurityQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<SecurityQuestion>> call, Response<ArrayList<SecurityQuestion>> response) {
                ArrayList<SecurityQuestion> securityQuestions = (ArrayList<SecurityQuestion>) response.body();
                Error error = parserError(securityQuestions.get(0));
                if (error == null) {
                    listener.success(securityQuestions);
                } else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SecurityQuestion>> call, Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
