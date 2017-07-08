package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.controlers.UserSecurityControler;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.SecurityQuestion;
import com.organize4event.organize.ui.adapters.SelectorSecurityQuestionAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectorSecurityQuestionActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listSecurityQuestion)
    RecyclerView listSecurityQuestion;
    private Context context;
    private int user_id = 0;
    private SecurityQuestion securityQuestionSelected;
    private ArrayList<SecurityQuestion> securityQuestions = new ArrayList<>();
    private SelectorSecurityQuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_security_question);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SECURITY QUESTION LIST");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = SelectorSecurityQuestionActivity.this;
        user_id = getIntent().getExtras().getInt("user_id");
        securityQuestionSelected = Parcels.unwrap(getIntent().getExtras().getParcelable("securityQuestion"));
        configureToolbar(context, toolbar, context.getString(R.string.label_security_questions), context.getResources().getDrawable(R.drawable.ic_arrow_back), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        listSecurityQuestion.setLayoutManager(new LinearLayoutManager(context));
        listSecurityQuestion.setItemAnimator(new DefaultItemAnimator());

        getData();
    }

    protected void getData() {
        new UserSecurityControler(context).getSecurityQuestions(user_id, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    securityQuestions = (ArrayList<SecurityQuestion>) object;
                    adapter = new SelectorSecurityQuestionAdapter(context, securityQuestions, securityQuestionSelected, new RecyclerViewListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent();
                            securityQuestionSelected = securityQuestions.get(position);
                            intent.putExtra("securityQuestion", Parcels.wrap(SecurityQuestion.class, securityQuestionSelected));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    listSecurityQuestion.setAdapter(adapter);
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }
}
