package com.jsn.android_firebase_masterclass.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class TokenProvider {

    companion object {
        fun getFirebaseToken(): String {
            var token = ""
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(
                            "MainActivity",
                            "Fetching FCM registration token failed",
                            task.exception
                        )
                        return@addOnCompleteListener
                    }

                    // Get new FCM registration token
                    token = task.result
                    Log.d("checkTokenFromActivity", "FCM token: $token")


                }
            return token
        }
    }
}
