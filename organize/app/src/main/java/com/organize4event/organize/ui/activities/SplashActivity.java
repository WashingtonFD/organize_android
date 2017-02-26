package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.organize4event.organize.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    Handler handler;
    private Context context;

    @Bind(R.id.txt_loading)
    TextView txt_loading;

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
        txt_loading.setVisibility(View.GONE);

        //TODO: implementar first access

        startActivity(new Intent(context, _TestActivity.class));
        finish();
    }
}
