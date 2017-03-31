package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.ui.fragments.HomeFragment;
import com.organize4event.organize.ui.fragments.InstitutionalFragment;
import com.organize4event.organize.ui.fragments.SettingsFragment;

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
        setupToolbar(context.getString(R.string.label_nav_home));
        if (!PreferencesManager.isLogged()){
            PreferencesManager.setIsLogged();
        }

        fragmentClass = HomeFragment.class;
        try{
            Log.v("instance HOME", "Home fragment instance");
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerContent, fragment).commit();
        }
        catch (Exception e){
            Log.v("instance HOME ERROR", "Home fragment instance error");
            e.printStackTrace();
        }
    }

    public void setupToolbar(String title){
        configureToolbar(context, toolbar, title, context.getResources().getDrawable(R.drawable.ic_menu_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @OnClick(R.id.imgNotification)
    public void actionOpenNotifications(){
        startActivity(new Intent(context, NotificationsActivity.class));
    }

    @OnClick({R.id.userContainer, R.id.homeContainer, R.id.eventContainer, R.id.sheduleContainer, R.id.partnerContainer, R.id.paymentContainer, R.id.purchaseContainer, R.id.settingsContainer, R.id.institutionalContainer})
    public void actionMenuSwitch(View view){
        switch (view.getId()){
            case R.id.userContainer:
                break;
            case R.id.homeContainer:
                setupToolbar(context.getString(R.string.label_nav_home));
                fragmentClass = HomeFragment.class;
                break;
            case R.id.eventContainer:
                break;
            case R.id.sheduleContainer:
                break;
            case R.id.partnerContainer:
                break;
            case R.id.paymentContainer:
                break;
            case R.id.purchaseContainer:
                break;
            case R.id.settingsContainer:
                setupToolbar(context.getString(R.string.label_nav_settings));
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.institutionalContainer:
                setupToolbar(context.getString(R.string.label_nav_institutional));
                fragmentClass = InstitutionalFragment.class;
                break;
            default:
                setupToolbar(context.getString(R.string.label_nav_home));
                fragmentClass = HomeFragment.class;
                break;
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        drawerLayout.closeDrawers();
    }
}
