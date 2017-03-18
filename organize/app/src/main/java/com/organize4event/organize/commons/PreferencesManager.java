package com.organize4event.organize.commons;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Token;
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

    public static Token getToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        Token token = null;
        Gson gson = new Gson();
        String tokenActive = preferences.getString("token", "");
        Type type = new TypeToken<User>(){}.getType();
        token = gson.fromJson(tokenActive, type);
        return token;
    }

    public static void saveToken(Token token){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if (token != null){
            Gson gson = new Gson();
            String tokenActive = gson.toJson(token);
            editor.putString("token", tokenActive);
            editor.commit();
        }
    }

    public static User getUser(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        User user = null;
        Gson gson = new Gson();
        String userLogged = preferences.getString("user", "");
        Type type = new TypeToken<User>(){}.getType();
        user = gson.fromJson(userLogged, type);
        return user;
    }

    public static void saveUser(User user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if (user != null){
            Gson gson = new Gson();
            String userLogged = gson.toJson(user);
            editor.putString("user", userLogged);
            editor.commit();
        }
    }
}
