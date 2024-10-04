package com.jsn.android_firebase_masterclass

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.jsn.android_firebase_masterclass.base.BaseClickActionActivity
import com.jsn.android_firebase_masterclass.utils.BundleKeys

class LandingActivity : BaseClickActionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extract data from the Intent
        val messageId = intent.getStringExtra("messageId")
        val userId = intent.getStringExtra("userId")

        if (intent.hasExtra(BundleKeys.IS_FROM_FIREBASE_PUSH_KEY)) {
            // Firebase Action
            performFirebaseAction(messageId, userId)
            Log.d("checkTestFB","coming from firebase push")
        } else {
            // Check for the dynamic link
            getDeepLinkData()
        }
    }

    private fun getDeepLinkData() {
        // Check for the dynamic link
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                val deepLink: Uri? = pendingDynamicLinkData?.link
                if (deepLink != null) {
                    // Handle the deep link
                    performDeepLinkAction(deepLink)

                }
            }
            .addOnFailureListener { e ->
                Log.w("CheckTestDLFail", "getDynamicLink:onFailure", e)
            }
    }
}