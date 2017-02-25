package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.organize4event.organize.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        context = WelcomeActivity.this;
    }

    @OnClick(R.id.goHome)
    public void actionGoHome(){
        startActivity(new Intent(context, HomeActivity.class));
    }
}
