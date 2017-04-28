package com.organize4event.organize.commons;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.models.FirstAccess;

import pl.tajchert.nammu.Nammu;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppApplication extends Application{
    private static AppApplication instance;
    private static FirstAccess firstAccess;
    private FirebaseAnalytics firebaseAnalytics;

    public void onCreate()
    {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        System.setProperty("http.keepAlive", "false");
        System.setProperty("http.maxConnections", "5");
        Nammu.init(instance);
        ApiClient.newInstance();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/GothamRounded-Light.otf").setFontAttrId(R.attr.fontPath).build());

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public static AppApplication getInstance() {
        return instance;
    }

    public static void setInstance(AppApplication instance) {
        AppApplication.instance = instance;
    }

    public static FirstAccess getFirstAccess() {
        if (firstAccess == null){
            firstAccess = PreferencesManager.getFirstAccess();
        }
        return firstAccess;
    }

    public static void setFirstAccess(FirstAccess firstAccess) {
        AppApplication.firstAccess = firstAccess;
    }
}
