package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.organize4event.organize.R;
import com.organize4event.organize.listener.ToolbarListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    private Context context;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        context = HomeActivity.this;

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_nav_home), context.getResources().getDrawable(R.drawable.ic_menu_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
