package com.organize4event.organize.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.model.FirstAccess;

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
    }

    public static FirstAccess getFirstAccess(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        FirstAccess firstAccess = null;
        Gson gson = new Gson();
        String first_access = preferences.getString("first_access", "");
        Type type = new TypeToken<FirstAccess>(){}.getType();
        firstAccess = gson.fromJson(first_access, type);
        return firstAccess;
    }
}
