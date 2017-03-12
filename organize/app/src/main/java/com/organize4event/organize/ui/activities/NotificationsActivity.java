package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.organize4event.organize.R;
import com.organize4event.organize.listener.ToolbarListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationsActivity extends BaseActivity {
    private Context context;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        context = NotificationsActivity.this;

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_notifications), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        getData();
    }

    public void getData(){
        //TODO: Wireframe - tela 06 - Elcio
    }
}
