package com.jsn.android_firebase_masterclass

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.dynamiclinks.ktx.socialMetaTagParameters
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<AppCompatButton>(R.id.shortLinkCreateBtn).setOnClickListener() {
            createDynamicLinkThenShortLink(this@MainActivity)
        }
    }

    // Here, in this method. it creates deep link first then short deep link automatically inside shortLinkAsync. So, it's not needed -> (deep link code + short link code).
    private fun createDynamicLinkThenShortLink(context: Context) {
        val id = 670
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://jsn.fmc/?invitedby=test-user2&id=$id")
            domainUriPrefix = "https://jsnfmc.page.link"
            androidParameters(context.packageName) {
                minimumVersion = 1
            }
//            iosParameters("ios-package-name") {
//                minimumVersion = "1.0"
//            }
            socialMetaTagParameters {
                title = "Deep Link"
                description = "share this link with your friends"
                imageUrl = Uri.parse("https://r.bing.com/rp/fY0wJ-QaIKghQ87Qs9ufsshTAws.png")
            }
        }.addOnSuccessListener { shortLinkResult ->
            findViewById<TextView>(R.id.shortLinkTv).text = shortLinkResult.shortLink.toString()
            Log.d("CheckTestSuccess", shortLinkResult.shortLink.toString())
        }.addOnFailureListener { fail ->
            Log.d("CheckTestError", "Deep Link Creation Failed:$fail")
        }
    }
}