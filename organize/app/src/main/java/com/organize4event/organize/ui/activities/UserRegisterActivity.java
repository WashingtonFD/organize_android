package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;

import com.organize4event.organize.R;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class UserRegisterActivity extends BaseActivity {
    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        context = UserRegisterActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));
    }
}
