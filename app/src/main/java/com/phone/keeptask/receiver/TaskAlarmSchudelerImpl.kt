package com.phone.keeptask.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.phone.keeptask.domain.model.Task
import com.phone.keeptask.helperFunction.Functions

class TaskAlarmSchudelerImpl(private val context: Context) : TaskAlarmSchudeler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun taskSchudeler(task: Task) {
        val intent = Intent(context, TaskReceiver::class.java).apply {
            putExtra("MESSAGE", task.name)
            putExtra("NUMBER", task.contactPhone)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            task.date,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun taskCancel(task: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                Intent(context, TaskReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Functions.toast(context, "Tache retiree")
    }

}