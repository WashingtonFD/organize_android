package com.organize4event.organize.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.organize4event.organize.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class _TestActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.primeiroacesso)
    public void action_pri(){

    }

    @OnClick(R.id.home)
    public void action_hom(){
        startActivity(new Intent(this, WelcomeActivity.class));
    }
}
