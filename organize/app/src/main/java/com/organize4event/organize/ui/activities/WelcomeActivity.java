package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.enuns.PlanEnum;
import com.organize4event.organize.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {
    private Context context;
    private User user;

    @Bind(R.id.txtWelcomeUser)
    TextView txtWelcomeUser;

    @Bind(R.id.txtPlan)
    TextView txtPlan;

    @Bind(R.id.txtPlanDescription)
    TextView txtPlanDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        context = WelcomeActivity.this;

        String[] user_full_name = user.getFull_name().split(" ");
        String welcome_user = "";
        if (user.getGender().equals("F")){
            welcome_user = context.getString(R.string.label_welcome_fem) + " " + user_full_name[0];
        }
        else {
            welcome_user = context.getString(R.string.label_welcome_masc) + " " + user_full_name[0];
        }

        txtWelcomeUser.setText(welcome_user);
        txtPlan.setText(user.getPlan().getName());
        txtPlanDescription.setText(user.getPlan().getDescription());

        PlanEnum planEnum = PlanEnum.values()[(user.getPlan().getCode_enum()) -1];

        switch (planEnum){
            case FREE:
                txtPlan.setTextColor(context.getResources().getColor(R.color.colorOrange));
                break;
            case BASIC:
                txtPlan.setTextColor(context.getResources().getColor(R.color.colorPurple));
                break;
            case PREMIUM:
                txtPlan.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
        }
    }

    @OnClick({R.id.btnTutorial, R.id.txtSkipTutorial})
    public void actionOnClick(View view){
        switch (view.getId()){
            case R.id.btnTutorial:
                showToastMessage(context, "Tutorial - Sprint 06");
                break;
            case R.id.txtSkipTutorial:
                PreferencesManager.hideWelcome();
                startActivity(new Intent(context, HomeActivity.class));
                finish();
                break;
        }
    }
}
