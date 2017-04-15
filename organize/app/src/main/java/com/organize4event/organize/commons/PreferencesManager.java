package com.organize4event.organize.commons;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.models.FirstAccess;

import java.lang.reflect.Type;

public class PreferencesManager {
    public static void saveFirstAccess(FirstAccess firstAccess){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if (firstAccess != null){
            Gson gson = new Gson();
            String first_access = gson.toJson(firstAccess);
            editor.putString("first_access", first_access);
            editor.commit();
        }
        else {
            editor.remove("first_access");
            editor.commit();
        }
    }

    public static FirstAccess getFirstAccess(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        FirstAccess firstAccess = null;
        Gson gson = new Gson();
        if (preferences.contains("first_access")){
            String first_access = preferences.getString("first_access", "");
            Type type = new TypeToken<FirstAccess>(){}.getType();
            firstAccess = gson.fromJson(first_access, type);
        }
        return firstAccess;
    }

    public static void hideWelcome(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("hideWelcome", true);
        editor.commit();
    }

    public static boolean isHideWelcome(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        return preferences.contains("hideWelcome");
    }

    public static void setIsLogged(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogged", true);
        editor.commit();
    }

    public static boolean isLogged(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        return preferences.contains("isLogged");
    }

}
