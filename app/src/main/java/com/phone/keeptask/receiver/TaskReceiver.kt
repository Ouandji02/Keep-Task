package com.phone.keeptask.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.phone.keeptask.service.SmS
import com.phone.keeptask.service.TaskService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val scope = CoroutineScope(Dispatchers.IO)
        val number = intent?.getStringExtra("NUMBER")
        intent?.getStringExtra("MESSAGE")?.let { message ->
            context?.let {
                val intent = Intent(context, TaskService::class.java)
                val sms = SmS(context)
                context.startService(intent)
                val notificationDisplay = NotificationManag(context)
                notificationDisplay.createChannelNotification()
                notificationDisplay.createNotification(message)
                sms.sendSmS(number.toString(), " C'est l'heure de notre programme : $message")
                scope.launch {
                    delay(30000)
                    context.stopService(intent)
                }
            }
        }
    }
}