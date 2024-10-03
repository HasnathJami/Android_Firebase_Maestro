package com.jsn.android_firebase_masterclass.service

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jsn.android_firebase_masterclass.LandingActivity
import com.jsn.android_firebase_masterclass.R
import com.jsn.android_firebase_masterclass.utils.BundleKeys

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        val dataPayload = remoteMessage.data
        val messageId = dataPayload["messageId"]
        val userId = dataPayload["userId"]

        Log.d("checkTestData1",title.toString())
        Log.d("checkTestData2",body.toString())
        Log.d("checkTestData3",dataPayload.toString())
        Log.d("checkTestData4",messageId.toString())
        Log.d("checkTestData5",userId.toString())
        showNotification(title, body, messageId, userId)
    }

    private fun createPendingIntent(messageId: String?, userId: String?): PendingIntent {
        val intent = Intent(this, LandingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(BundleKeys.IS_FROM_FIREBASE_PUSH_KEY, true)
            putExtra(BundleKeys.MESSAGE_ID_KEY, messageId)
            putExtra(BundleKeys.USER_ID_KEY, userId)
        }
        return PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun showNotification(
        title: String?,
        message: String?,
        messageId: String?,
        userId: String?
    ) {
        val notificationBuilder = NotificationCompat.Builder(this, "my_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_background).setContentTitle(title)
            .setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(createPendingIntent(messageId, userId)).setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}