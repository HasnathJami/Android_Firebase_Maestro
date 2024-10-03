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
            createDynamicThenShortLink(this@MainActivity)
        }
    }

    private fun createDynamicThenShortLink(context: Context) {
        val id = 67
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://www.jsn-jami.com/invitedby=jami&age=${id}")
            domainUriPrefix = "https://example.page.link/summer-sale"
            androidParameters(context.packageName) {
                minimumVersion = 1
            }
//            iosParameters("com.jsn") {
//                minimumVersion = "1.0"
//            }
            socialMetaTagParameters {
                title = "Deep Link"
                description = "share this link with your friends"
//                imageUrl = Uri.parse("https://r.bing.com/rp/fY0wJ-QaIKghQ87Qs9ufsshTAws.png")
            }
        }.addOnSuccessListener { shortLink ->
            findViewById<TextView>(R.id.shortLinkTv).text = shortLink.toString()
            Log.d("CheckTestSuccess", shortLink.toString())
        }.addOnFailureListener { fail ->
            Log.d("CheckTestError", "Deep Link Creation Failed:$fail")
        }
    }

    // Function to create a dynamic link


}