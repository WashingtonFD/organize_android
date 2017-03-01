package com.organize4event.organize.common;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.organize4event.organize.R;

import pl.tajchert.nammu.Nammu;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppApplication extends Application{
    private static AppApplication sInstance;

    public void onCreate()
    {
        super.onCreate();
        MultiDex.install(this);
        sInstance = this;
        System.setProperty("http.keepAlive", "false");
        System.setProperty("http.maxConnections", "5");
        Nammu.init(sInstance);
        ApiClient.newInstance();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/GothamRounded-Light.otf").setFontAttrId(R.attr.fontPath).build());
    }

    public static AppApplication getsInstance() {
        return sInstance;
    }

    public static void setsInstance(AppApplication sInstance) {
        AppApplication.sInstance = sInstance;
    }
}
