package com.example.krishbhatia.eduassets;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferenceImpl {
    private static final String APP_SETTINGS = "APP_SETTINGS";


    // properties
    private static final String SOME_STRING_VALUE = "SOME_STRING_VALUE";
    // other properties...


    private SharedPreferenceImpl() {}

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getSomeStringValue(Context context,String key) {
        return getSharedPreferences(context).getString(key , null);
    }

    public static void setSomeStringValue(Context context, String key,String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, newValue);
        editor.apply();
    }

    // other getters/setters
}

