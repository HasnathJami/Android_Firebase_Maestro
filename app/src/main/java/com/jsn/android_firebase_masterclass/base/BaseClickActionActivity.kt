package com.jsn.android_firebase_masterclass.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jsn.android_firebase_masterclass.MainActivity
import com.jsn.android_firebase_masterclass.UserDetailsActivity
import com.jsn.android_firebase_masterclass.enums.ClickActionType
import com.jsn.android_firebase_masterclass.utils.BundleKeys
import com.jsn.android_firebase_masterclass.utils.QueryParameterKeys

abstract class BaseClickActionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("checkCall","BCAA Call")
    }

    protected fun performDeepLinkAction(deepLinkUri: Uri?) {
        deepLinkUri?.let {
//            val invitedBy = deepLinkUri.getQueryParameter("invitedby")
//            val id = deepLinkUri.getQueryParameter("id")
            val bundle = Bundle()
            bundle.putString(BundleKeys.CLICK_ACTION_KEY,
                ClickActionType.USER_DETAILS.toString()
            )
            if (deepLinkUri.getQueryParameter(QueryParameterKeys.INVITED_BY_QUERY_PARAMETER_KEY) != null) {
                bundle.putString(BundleKeys.INVITED_BY_KEY, deepLinkUri.getQueryParameter(QueryParameterKeys.INVITED_BY_QUERY_PARAMETER_KEY))
            }
            if (deepLinkUri.getQueryParameter("id") != null) {
                bundle.putString(BundleKeys.ID_KEY, deepLinkUri.getQueryParameter(QueryParameterKeys.ID_QUERY_PARAMETER_KEY))
            }
            showClickActionActivity(bundle)

        } ?: run {
            Toast.makeText(this, "Deep link null", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    protected fun showClickActionActivity(extras: Bundle?) {
        when (extras?.getString(BundleKeys.CLICK_ACTION_KEY)) {
            ClickActionType.USER_DETAILS.toString() -> {
                val intent = Intent(this, UserDetailsActivity::class.java)
                intent.putExtras(extras)
                startActivity(intent)
                finish()
            }

        }
    }


}