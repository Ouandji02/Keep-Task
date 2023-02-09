package com.phone.keeptask.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.phone.keeptask.service.TaskService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val scope = CoroutineScope(Dispatchers.IO)
        intent?.getStringExtra("MESSAGE")?.let { message ->
            context?.let {
                val intent = Intent(context, TaskService::class.java)
                context.startService(intent)
                val notificationDisplay = NotificationManag(context)
                notificationDisplay.createChannelNotification()
                notificationDisplay.createNotification(message)
                scope.launch {
                    delay(10000)
                    context.startService(intent)
                }
            }
        }
    }
}