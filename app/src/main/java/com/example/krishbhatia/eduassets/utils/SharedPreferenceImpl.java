package com.example.krishbhatia.eduassets.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.google.gson.Gson;


public class SharedPreferenceImpl {

    public static final String NOT_FOUND = "NOT_FOUND";
    private static SharedPreferenceImpl singleton = new SharedPreferenceImpl();

    public SharedPreferenceImpl() {
    }
    public static SharedPreferenceImpl getInstance( ) {
        return singleton;
    }



    public String get(String key, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPref.getString(key, NOT_FOUND);
        return value;
    }


    public void save(String key, String value, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public void remove(String key, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }
    public void clearAll(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public Boolean contains(String key, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.contains(key);
    }
    public void addUserPojo(UserPOJO userPOJO, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userPOJO);
        prefsEditor.putString(Constants.USERPOJO, json);
        prefsEditor.apply();
    }


}
