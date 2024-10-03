package com.jsn.android_firebase_masterclass

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class DeepLinkLandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link_landing)
        Log.d("CheckTestDLink", "callFromDeepLink")

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
                    Log.d("CheckTestDLink", "callFromDeepLinkNotNull")
                    val invitedBy = deepLink.getQueryParameter("invitedby")
                    val id = deepLink.getQueryParameter("id")
                    // Do something with the value
                    Log.d("CheckTestDynamicLinkNM", "Invited by: $invitedBy")
                    Log.d("CheckTestDynamicLinkID", "ID: $id")
                }
            }
            .addOnFailureListener { e ->
                Log.w("CheckTestDynamicLFail", "getDynamicLink:onFailure", e)
            }
    }
}