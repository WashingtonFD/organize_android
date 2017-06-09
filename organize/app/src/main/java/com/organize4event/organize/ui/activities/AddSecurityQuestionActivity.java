package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.ToolbarListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddSecurityQuestionActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_security_question);
        ButterKnife.bind(this);
        context = AddSecurityQuestionActivity.this;

        configureToolbar(context, toolbar, context.getString(R.string.label_new_security_question), context.getResources().getDrawable(R.drawable.ic_arrow_back), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
