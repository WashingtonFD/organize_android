package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanIdentifierActivity extends BaseActivity {
    private Context context;
    private User user;
    private ArrayList<Plan> plans = new ArrayList<>();

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

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_plan_identifier), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


        listPlans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.btnPlanFree:
                        user.setPlan(plans.get(0));
                        containerValidateCode.setVisibility(View.GONE);
                        break;
                    case R.id.btnPlanBasic:
                        user.setPlan(plans.get(1));
                        containerValidateCode.setVisibility(View.VISIBLE);
                        break;
                    case R.id.btnPlanPremium:
                        user.setPlan(plans.get(2));
                        containerValidateCode.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

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
        //TODO: INSTANCIAR GET PLAN - ALTERAR ROTA PARA PASSAR A LINGUAGEM
    }

    public void validateCodePlan(){
        if (user.getPlan() != plans.get(0)){
            //TODO: VALIDAR CODIGO.
        }

        //TODO: INSTANCIAR ACTIVITY IDENTIFICAÇÃO DO USUARIO.
    }
}
