package com.phone.keeptask.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.phone.keeptask.MainActivity
import com.phone.keeptask.R
import com.phone.keeptask.constant.K

class NotificationManag(private val context: Context) {
    fun createNotification(taskName: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            0
        )
        val notificationManager = NotificationManagerCompat.from(context)
        val builder = NotificationCompat.Builder(context, K.CHANNEL_ID_NOTIFICATION)
            .setContentTitle("Rappel de tache".toString())
            .setContentText("vous avez".toString()+ " " + taskName)
            .setSmallIcon(R.drawable.icon_task)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(K.ID_NOTIFICATION, builder)

    }

    fun createChannelNotification() {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            K.CHANNEL_ID_NOTIFICATION,
            K.NAME_NOTIFICATION_CHANNEL,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = K.DESCRIPTION
        notificationManager.createNotificationChannel(channel)
    }
}