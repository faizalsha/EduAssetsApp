package com.example.krishbhatia.eduassets.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class StarterApplication extends Application {
    public static Context context;

    private static final String TAG = "StarterApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: print");
        context=getApplicationContext();

    }

    public static void setContext(Context context) {
        StarterApplication.context = context;
    }
}
