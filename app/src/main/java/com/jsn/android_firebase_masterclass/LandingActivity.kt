package com.jsn.android_firebase_masterclass

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.jsn.android_firebase_masterclass.base.BaseClickActionActivity

class LandingActivity : BaseClickActionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for the dynamic link
        Log.d("checkCall","LD Call")
        getDeepLinkData()

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