package com.jsn.android_firebase_masterclass

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jsn.android_firebase_masterclass.utils.BundleKeys

class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        if (intent.hasExtra(BundleKeys.ID_KEY)) {
            val id = intent.getStringExtra(BundleKeys.ID_KEY)
            findViewById<TextView>(R.id.idTv).text = id
        }

        if (intent.hasExtra(BundleKeys.INVITED_BY_KEY)) {
            val name = intent.getStringExtra(BundleKeys.INVITED_BY_KEY)
            findViewById<TextView>(R.id.invitedByTv).text = name
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(intent.hasExtra(BundleKeys.CLICK_ACTION_KEY)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}