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
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

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
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        context = SplashActivity.this;
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        locale = Locale.getDefault().toString();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 5000);
    }

    protected void getData(){
        firstAccess = AppApplication.getFirstAccess();
        if (firstAccess == null){
            getFirstAccess();
        }
    }

    protected void getFirstAccess(){
        new FirstAccessControll(context).getFirstAccess(device_id, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                firstAccess = (FirstAccess) object;
                if (firstAccess.is_new()){
                    setFirstAccess();
                }
                else {
                    user = firstAccess.getUser();
                    if (user.getUser_term() == null || !user.getUser_term().isTerm_accept()){
                        startApresentationActivity();
                    }
                    else if(user.getPlan() == null){
                        startPlanIdentifierActivity();
                    }
                    else if(user.getUser_type() == null){
                        startUserRegisterActivity();
                    }
                    else if (user.getToken() == null || !user.getToken().isKeep_logged()){
                        starLoginActivity();
                    }
                    else if (PreferencesManager.isHideWelcome()){
                        startActivity(new Intent(context, HomeActivity.class));
                    }
                    else{
                        startActivity(new Intent(context, WelcomeActivity.class));
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void setFirstAccess(){
        user = new User();
        firstAccess.setUser(user);
        firstAccess.setDevice_id(device_id);
        firstAccess.setLocale(locale);
        firstAccess.setInstalation_date(new Date());

        PreferencesManager.saveFirstAccess(firstAccess);
        startApresentationActivity();


    }

    protected void startApresentationActivity(){
        Intent intent = new Intent(context, ApresentationActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void startPlanIdentifierActivity(){
        Intent intent = new Intent(context, PlanIdentifierActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void startUserRegisterActivity(){
        Intent intent = new Intent(context, UserRegisterActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starLoginActivity(){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }
}
