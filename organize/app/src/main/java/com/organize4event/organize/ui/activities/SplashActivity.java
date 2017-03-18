package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.FirstAccessControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;

import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    private Context context;
    private String locale;
    private String device_id;
    private FirstAccess firstAccess;

    Handler handler;

    @Bind(R.id.txtLoading)
    TextView txtLoading;

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
        txtLoading.setVisibility(View.GONE);
        locale = Locale.getDefault().toString();
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        firstAccess = AppApplication.getFirstAccess();
        if (firstAccess == null){
            getFirstAccess();
        }
        else {
            startActivit(_TestActivity.class);
        }
    }

    public void getFirstAccess(){
        new FirstAccessControll(context).getFirstAccess(device_id, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                firstAccess = (FirstAccess) object;
                if (firstAccess.getId() == 0){
                    saveFirstAccess();
                }
                else{
                    getUser();
                }
            }

            @Override
            public void fail(Error error) {
                if(isOline(context)){
                    showDialogMessage(context.getResources().getString(R.string.error_title), error.getMessage());
                }
                else {
                    showDialogMessage(context.getResources().getString(R.string.error_title), context.getResources().getString(R.string.error_message_conect));
                }
            }
        });
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
                startActivit(_TestActivity.class);
            }

            @Override
            public void fail(Error error) {
                if(isOline(context)){
                    showDialogMessage(context.getResources().getString(R.string.error_title), error.getMessage());
                }
                else {
                    showDialogMessage(context.getResources().getString(R.string.error_title), context.getResources().getString(R.string.error_message_conect));
                }
            }
        });
    }



    public void getUser(){
        // TODO: implementar getUser()

        getToken();
    }

    public void getToken(){
        // TODO: implementar getToken()

        startActivit(_TestActivity.class); //TODO: mudar para apresentation activity
    }

    public void startActivit(Class classActivity){
        startActivity(new Intent(context, classActivity));
        finish();
    }
}
