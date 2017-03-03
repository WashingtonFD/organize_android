package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.organize4event.organize.R;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    private Context context;
    private String locale;
    private String device_id;

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

        //TODO: implementar first access

        startActivity(new Intent(context, _TestActivity.class));
        finish();
    }
}
