package com.jsn.android_firebase_masterclass;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyApplication extends Application {
    public static DatabaseReference databaseRef;
    public static CollectionReference firestoreCollectionRef;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        // Enable Crashlytics reporting based on build type
//      val isDebug = BuildConfig.DEBUG
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);  //use this line for specially the debug build. On release build, firebase automatically enabled it.


        //Firebase Realtime Database
        initFirebaseRealtimeDatabase();


        //FireStore Database
        initFireStoreDatabase();
    }

    private void initFirebaseRealtimeDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("items");
    }

    private void initFireStoreDatabase() {
        firestoreCollectionRef = FirebaseFirestore.getInstance().collection("productItems");
    }

}
