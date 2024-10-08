package com.jsn.android_firebase_masterclass;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        // Enable Crashlytics reporting based on build type
//        val isDebug = BuildConfig.DEBUG

         //use the below line for specially the debug build. On release build, firebase automatically enabled it.
         FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
}
