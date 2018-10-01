package com.app.findmycafeplus.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.app.findmycafeplus.R

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MessagingService"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        sendNotification(remoteMessage!!.notification!!.body)

    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: " + token!!)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(token: String?) {
        var notimanager = NotificationCompat.Builder(this)
        notimanager.setContentTitle("Notification Title")
        notimanager.setContentText("Notification Content")
        notimanager.setSmallIcon(R.drawable.ic_logo)
        notimanager.setAutoCancel(true)
        notimanager.setChannelId("0")

        val notificationChannel = NotificationChannel("0","channel",NotificationManager.IMPORTANCE_HIGH)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(notificationChannel)

        manager.notify(0,notimanager.build())
    }
}
