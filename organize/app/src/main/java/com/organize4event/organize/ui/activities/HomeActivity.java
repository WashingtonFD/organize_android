package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controllers.FirstAccessControll;
import com.organize4event.organize.controllers.NotificationControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.ui.fragments.HomeFragment;
import com.organize4event.organize.ui.fragments.InstitutionalFragment;
import com.organize4event.organize.ui.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.organize4event.organize.R.id.containerContent;

public class HomeActivity extends BaseActivity {
    private Context context;
    private String device_id;
    private FirstAccess firstAccess;
    private ArrayList<UserNotification> userNotifications = new ArrayList<>();
    Class fragmentClass;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imgNotification)
    ImageView imgNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        context = HomeActivity.this;
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        setupToolbar(context.getString(R.string.label_nav_home));
        if (!PreferencesManager.isLogged()){
            PreferencesManager.setIsLogged();
        }

        fragmentClass = HomeFragment.class;
        try{
            Log.v("instance HOME", "Home fragment instance");
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(containerContent, fragment).commit();
        }
        catch (Exception e){
            Log.v("instance HOME ERROR", "Home fragment instance error");
            e.printStackTrace();
        }

        getData();
//        verifyData();
    }

    protected void setupToolbar(String title){
        configureToolbar(context, toolbar, title, context.getResources().getDrawable(R.drawable.ic_menu_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    protected void getData(){
        new FirstAccessControll(context).getFirstAccess(device_id, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                firstAccess = (FirstAccess) object;
                PreferencesManager.saveFirstAccess(firstAccess);
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void getNotifications(){
        new NotificationControll(context).getUserNotifications(firstAccess.getUser().getId(), new ControllResponseListener() {
            @Override
            public void success(Object object) {
                userNotifications = (ArrayList<UserNotification>) object;
                assetIconNotification();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void assetIconNotification(){
        int count = 0;
        if (userNotifications.size() > 0){
            for (UserNotification userNotification : userNotifications){
                if (!userNotification.is_read()){
                    count ++;
                }
            }
        }

        if (count > 0){
            imgNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp_on));
            imgNotification.setColorFilter(context.getResources().getColor(R.color.colorTransparent));
        }
        else{
            imgNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
            imgNotification.setColorFilter(context.getResources().getColor(R.color.colorDestakText));
        }
    }

    protected void verifyData(){
        int delay = 5000;
        int interval = 10000;
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getNotifications();
            }
        }, delay, interval);
    }

    @OnClick(R.id.imgNotification)
    public void actionOpenNotifications(){
        Intent intent = new Intent(context, NotificationsActivity.class);
        intent.putParcelableArrayListExtra("userNotifications", userNotifications);
        startActivity(intent);
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
            fragmentManager.beginTransaction().replace(containerContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        drawerLayout.closeDrawers();
    }
}
