package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controllers.FirstAccessControll;
import com.organize4event.organize.controllers.TokenControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    Handler handler;
    @Bind(R.id.txtLoading)
    TextView txtLoading;
    private Context context;
    private String locale;
    private String device_id;
    private FirstAccess firstAccess;
    private Token token;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        context = SplashActivity.this;

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 5000);
    }

    public void getData(){
        firstAccess = AppApplication.getFirstAccess();
        token = AppApplication.getToken();
        user = AppApplication.getUser();

        locale = Locale.getDefault().toString();
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        getFirstAccess();
    }

    public void getFirstAccess(){
        if (firstAccess == null){
            new FirstAccessControll(context).getFirstAccess(device_id, new ControllResponseListener() {
                @Override
                public void sucess(Object object) {
                    firstAccess = (FirstAccess) object;
                    if (firstAccess.is_new()){
                        saveFirstAccess();
                    }
                    else{
                        PreferencesManager.saveFirstAccess(firstAccess);
                        AppApplication.setFirstAccess(firstAccess);
                        getToken();
                    }
                }

                @Override
                public void fail(Error error) {
                    returnErrorMessage(error, context);
                }
            });
        }
        else{
            getToken();
        }
    }

    public void saveFirstAccess(){
        firstAccess = new FirstAccess();
        firstAccess.setDevice_id(device_id);
        firstAccess.setLocale(locale);
        firstAccess.setInstalation_date(new Date());

        new FirstAccessControll(context).saveFirstAccess(firstAccess, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                firstAccess = (FirstAccess) object;
                startActivity(new Intent(context, ApresentationActivity.class));
                finish();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void getToken(){
        if (token == null || user == null){
            new TokenControll(context).getToken(firstAccess, new ControllResponseListener() {
                @Override
                public void sucess(Object object) {
                    token = (Token) object;
                    if (token.is_new()){
                        startActivity(new Intent(context, ApresentationActivity.class));
                        finish();
                    }
                    else{
                        PreferencesManager.saveToken(token);
                        AppApplication.setToken(token);
                        if (token.getUser() == null){
                            startActivity(new Intent(context, ApresentationActivity.class));
                            finish();
                        }
                        else{
                            user = token.getUser();
                            PreferencesManager.saveUser(user);
                            AppApplication.setUser(user);
                            getTermUser();
                        }
                    }
                }

                @Override
                public void fail(Error error) {
                    returnErrorMessage(error, context);
                }
            });
        }
        else {
            getTermUser();
        }
    }

    public void getTermUser(){
        if (user.getTerm() == null || !user.isTerm_accept()){
            startActivity(new Intent(context, ApresentationActivity.class));
            finish();
        }
        else{
            getPlanUser();
        }
    }

    public void getPlanUser(){
        if (user.getPlan() == null){
            startActivity(new Intent(context, PlanIdentifierActivity.class));
            finish();
        }
        else{
            getUser();
        }
    }

    public void getUser(){
        if (user.getMail() == null || user.getCpf() == null){
            startActivity(new Intent(context, UserRegisterActivity.class));
            finish();
        }
        else{
            getKeepLogin();
        }
    }

    public void getKeepLogin(){
        if (token.isKeep_logged()){
            if (PreferencesManager.isHideWelcome()){
                startActivity(new Intent(context, HomeActivity.class));
            }
            else{
                startActivity(new Intent(context, WelcomeActivity.class));
            }
            finish();
        }
        else{
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }
}
