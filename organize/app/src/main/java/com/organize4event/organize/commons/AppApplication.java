package com.organize4event.organize.commons;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.organize4event.organize.R;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import pl.tajchert.nammu.Nammu;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppApplication extends Application{
    private static AppApplication instance;
    private static FirstAccess firstAccess;
    private static Token token;
    private static User user;

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

    public static Token getToken() {
        if (token == null){
            token = PreferencesManager.getToken();
        }

        return token;
    }

    public static void setToken(Token token) {
        AppApplication.token = token;
    }

    public static User getUser() {
        if (user == null){
            user = PreferencesManager.getUser();
        }

        return user;
    }

    public static void setUser(User user) {
        AppApplication.user = user;
    }
}
