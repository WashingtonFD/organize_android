package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.organize4event.organize.R;
import com.organize4event.organize.listener.ToolbarListener;
import com.organize4event.organize.ui.fragments.HomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    private Context context;
    Class fragmentClass;

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

        fragmentClass = HomeFragment.class;
        try{
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerContent, fragment).commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imgNotification)
    public void actionOpenNotifications(){
        startActivity(new Intent(context, NotificationsActivity.class));
    }
}
