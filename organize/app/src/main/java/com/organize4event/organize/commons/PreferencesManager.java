package com.organize4event.organize.commons;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.User;

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

    public static void saveUserLogged(User user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if (user != null){
            Gson gson = new Gson();
            String userLogged = gson.toJson(user);
            editor.putString("user", userLogged);
            editor.commit();
        }
    }

    public static User getUserLogged(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        User user = null;
        Gson gson = new Gson();
        String userLogged = preferences.getString("user", "");
        Type type = new TypeToken<User>(){}.getType();
        user = gson.fromJson(userLogged, type);
        return user;
    }
}
