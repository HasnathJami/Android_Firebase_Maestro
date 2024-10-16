package com.jsn.android_firebase_masterclass.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jsn.android_firebase_masterclass.MainActivity
import com.jsn.android_firebase_masterclass.R
import com.jsn.android_firebase_masterclass.model.Item
import com.jsn.android_firebase_masterclass.model.SocialAccProviderData
import com.jsn.android_firebase_masterclass.network.database.dao.FirebaseFireStoreDao
import com.jsn.android_firebase_masterclass.ui.item.ItemListActivity

class LoginActivity : AppCompatActivity() {
    private val TAG = this.javaClass.name
    private val RC_SIGN_IN = 9001
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mFirebaseAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.googleSignInButton).setOnClickListener {
            signInWithGoogle()
        }

        findViewById<Button>(R.id.mainPageButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.listButton).setOnClickListener {
            startActivity(Intent(this, ItemListActivity::class.java))
        }

        if (!getTokenFromSharedPrefs().isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        insertDataToDB()

    }

    private fun insertDataToDB() {
        val item = Item(
            id = "3",
            name = "Jami HC",
            description = "Test J",
            imageUrl = "https://picsum.photos/200/300"
        )
        //Insert Data to Real Time DB
        // FirebaseDao.addItem(item)

        //Insert Data to Real Time DB
        FirebaseFireStoreDao.addItem(item)
    }

    private fun signInWithGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mFirebaseAuth = FirebaseAuth.getInstance()
        val mSignInClient = GoogleSignIn.getClient(this, gso)

        try {
            mFirebaseAuth.signOut()
            mSignInClient.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    fetchSocialLoginUserIdToken("google", task)
                } else {
                    Log.w(TAG, "firebaseAuthWithGoogle:failure", task.exception)
                }
            }
            .addOnFailureListener(this) { e ->
                Toast.makeText(
                    this, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun fetchSocialLoginUserIdToken(type: String, task: Task<AuthResult>) {
        Log.d(TAG, "firebaseAuthWith  $type : success ${task.result} ")

        mFirebaseAuth.currentUser?.getIdToken(true)?.addOnCompleteListener {

            try {
                if (it.isSuccessful) {
                    Log.d(TAG, "finalSocialResult: token: ${it.result?.token}")
                    verifySocialLoginWithAppServer(task, it.result?.token)
                } else {
                    Log.d(TAG, "finalSocialResult: token: token fetch failed")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(Exception::class)
    private fun verifySocialLoginWithAppServer(task: Task<AuthResult>, token: String?) {

        token?.let { token ->
            task.result.user?.providerData?.let {

                it[1].let { provider ->
                    val prodviderData = SocialAccProviderData(
                        providerId = provider.providerId,
                        uid = provider.uid,
                        displayName = provider.displayName,
                        email = provider.email,
                        phoneNumber = provider.phoneNumber,
                        photoURL = provider.photoUrl.toString()
                    )
                    //val jsonString = Gson().toJson(prodviderData)
                    // Log.d(TAG, "performSocialLogin: $jsonString")
                    Log.d("checkLogin", "Login Successful: ${prodviderData.uid}")
                    // subscribeUiToSocialLogin(prodviderData.uid, prodviderData.displayName,prodviderData.email)
                }
            }
        }
    }

    private fun saveTokenToSharedPrefs(token: String) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("TOKEN", token)
            apply()
        }
    }

    private fun getTokenFromSharedPrefs(): String? {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("TOKEN", null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == RC_SIGN_IN) {
                val result = GoogleSignIn.getSignedInAccountFromIntent(data)
                // handleSignInResult(task)
                result.getResult(ApiException::class.java)?.let { firebaseAuthWithGoogle(it) }
//            GoogleSignIn.getSignedInAccountFromIntent(data)
//                .addOnSuccessListener { account ->
//                   // handleSignInResult(it)
//                    Log.d("checkToken", account.idToken!!)
//                }
//                .addOnFailureListener { e->
//                    Log.d("checkToken", e.toString())
//                }


            }
        } catch (e: Exception) {
            Log.d("checkTokenException", e.toString())
        }

    }

}