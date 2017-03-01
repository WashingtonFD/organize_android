package com.organize4event.organize.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @OnClick({R.id.primeiroacesso, R.id.home})
    public void action_click(View view){
        switch (view.getId()){
            case R.id.primeiroacesso:
                startActivity(new Intent(this, TermUseActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
        }
    }
}
