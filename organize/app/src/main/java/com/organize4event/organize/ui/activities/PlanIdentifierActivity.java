package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.CustomValidate;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controlers.PlanControler;
import com.organize4event.organize.enuns.PlanEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class PlanIdentifierActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rgpListPlans)
    RadioGroup rgpListPlans;
    @Bind(R.id.containerValidateCode)
    RelativeLayout containerValidateCode;
    @Bind(R.id.txtValidateCode)
    EditText txtValidateCode;
    private Context context;
    private int plan_switch;
    private String title = "";
    private String message = "";
    private User user;
    private FirstAccess firstAccess;
    private ArrayList<Plan> plans = new ArrayList<>();
    private Plan plan;
    private CustomValidate customValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_identifier);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "PLAN IDENTIFIER");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = PlanIdentifierActivity.this;
        plan_switch = PlanEnum.FREE.getValue();
        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
        user = firstAccess.getUser();
        plan = new Plan();

        configureToolbar(context, toolbar, context.getString(R.string.label_plan_identifier), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        getPlan();
        selectPlan();
    }

    protected void selectPlan() {
        rgpListPlans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.btnPlanFree:
                        plan_switch = PlanEnum.FREE.getValue();
                        containerValidateCode.setVisibility(View.GONE);
                        setPlan();
                        break;
                    case R.id.btnPlanBasic:
                        plan_switch = PlanEnum.BASIC.getValue();
                        containerValidateCode.setVisibility(View.VISIBLE);
                        setPlan();
                        break;
                    case R.id.btnPlanPremium:
                        plan_switch = PlanEnum.PREMIUM.getValue();
                        containerValidateCode.setVisibility(View.VISIBLE);
                        setPlan();
                        break;
                }
            }
        });
    }

    protected void getPlan() {
        new PlanControler(context).getPlan(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                plans = (ArrayList<Plan>) object;
                setPlan();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void setPlan() {
        if (plans.size() > 0) {
            for (int i = 0; i < plans.size(); i++) {
                if (plans.get(i).getCode_enum() == plan_switch) {
                    plan = plans.get(i);
                    break;
                }
            }
        }
    }

    @OnFocusChange(R.id.txtValidateCode)
    public void actionOnFocus() {
        title = context.getString(R.string.app_name);
        message = context.getString(R.string.message_info_code_plan);
        hideOrShowInfoIcon(title, message, txtValidateCode);
    }


    @OnClick(R.id.imgAccept)
    public void actionSavePlan() {
        customValidate = new CustomValidate();
        boolean validate = true;
        if (plan.getCode_enum() != PlanEnum.FREE.getValue()) {
            validate = customValidate.validateCodePlan(txtValidateCode, plan);
        }

        if (validate) {
            user.setPlan(plan);
            firstAccess.setUser(user);
            PreferencesManager.saveFirstAccess(firstAccess);

            startUserRegisterActivity();
        }
    }

    protected void startUserRegisterActivity() {
        Intent intent = new Intent(context, UserRegisterActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }
}
