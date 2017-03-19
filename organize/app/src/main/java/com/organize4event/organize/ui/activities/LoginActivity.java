package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    private Context context;
    private User user;
    private FirstAccess firstAccess;
    private Token token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = LoginActivity.this;

        user = AppApplication.getUser();
        firstAccess = AppApplication.getFirstAccess();
        token = AppApplication.getToken();

        if (token != null && token.isKeep_logged()){
            startActivity(new Intent(context, HomeActivity.class));
        }
        else{
            token = new Token();
            token.setFirstAccess(firstAccess);
            token.setUser(user);
        }
    }
}
