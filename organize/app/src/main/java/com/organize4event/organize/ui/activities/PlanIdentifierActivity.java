package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.PlanControll;
import com.organize4event.organize.enuns.PlanEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanIdentifierActivity extends BaseActivity {
    private Context context;
    private int plan_switch = PlanEnum.FREE.getValue();

    private User user;
    private FirstAccess firstAccess;
    private ArrayList<Plan> plans = new ArrayList<>();
    private Plan plan;


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.listPlans)
    RadioGroup listPlans;

    @Bind(R.id.containerValidateCode)
    RelativeLayout containerValidateCode;

    @Bind(R.id.txtValidateCode)
    EditText txtValidateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_identifier);
        ButterKnife.bind(this);

        context = PlanIdentifierActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));
        firstAccess = AppApplication.getFirstAccess();

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_plan_identifier), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        getPlan();
        selectPlan();
        instanceInfo();
    }

    public void selectPlan(){
        listPlans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
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

    public void instanceInfo(){
        txtValidateCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (txtValidateCode.getRight() - txtValidateCode.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialogMessage(context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.message_validate_code_plan));
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void getPlan(){
        new PlanControll(context).getPlan(firstAccess.getLocale(), new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                plans = (ArrayList<Plan>) object;
                setPlan();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void setPlan(){
        if (plans.size() > 0){
            for(int i = 0; i < plans.size(); i++){
                if (plans.get(i).getCode_enum() == plan_switch){
                    plan = plans.get(i);
                    user.setPlan(plan);
                }
            }
        }
    }

    public boolean validateCodePlan(){
        String code = txtValidateCode.getText().toString();
        return code.equals(plan.getSecurity_code());
    }

    @OnClick(R.id.imgAccept)
    public void actionSavePlan(){
        boolean validate = true;
        if (plan.getCode_enum() != PlanEnum.FREE.getValue()){
            validate = validateCodePlan();
        }

        if (validate){
            Intent intent = new Intent(context, UserRegisterActivity.class);
            intent.putExtra("user", Parcels.wrap(User.class, user));
            startActivity(intent);
            finish();
        }
    }
}