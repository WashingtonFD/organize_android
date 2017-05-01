package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.enuns.PlanEnum;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.PlanPrice;
import com.organize4event.organize.ui.adapters.PlanAdvantagesAdapter;

import org.parceler.Parcels;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanDetailActivity extends BaseActivity {
    private Context context;
    private Plan plan;
    private PlanAdvantagesAdapter adapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txtPrice)
    TextView txtprice;
    @Bind(R.id.txtPeriod)
    TextView txtPeriod;
    @Bind(R.id.txtDescription)
    TextView txtDescription;
    @Bind(R.id.listAdvantages)
    RecyclerView listAdvantages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "PLAN DETAIL");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = PlanDetailActivity.this;
        plan = Parcels.unwrap(getIntent().getExtras().getParcelable("plan"));

        configureToolbar(context, toolbar, context.getString(R.string.label_plan) + " " + plan.getName(), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        adapter = new PlanAdvantagesAdapter(context, plan.getAdvantages());
        txtDescription.setText(plan.getDescription());

        if (plan.getCode_enum() == PlanEnum.FREE.getValue()){
            txtPeriod.setVisibility(View.GONE);
            txtprice.setText(context.getString(R.string.message_free));
        }
        else{
            txtPeriod.setVisibility(View.VISIBLE);
            for (PlanPrice planPrice : plan.getPrice()) {
                if (planPrice.is_active()) {
                    txtprice.setText(String.format(Locale.getDefault(), "%.2f", planPrice.getPrice()));
                    break;
                }
            }
        }

        listAdvantages.setLayoutManager(new LinearLayoutManager(context));
        listAdvantages.setItemAnimator(new DefaultItemAnimator());
        listAdvantages.setAdapter(adapter);
    }
}
