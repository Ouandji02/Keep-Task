package com.phone.keeptask.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager

class SmS(private val context: Context) {
    fun sendSmS(phoneNumber: String, data: String) {
        val sendPI = PendingIntent.getBroadcast(
            context,
            0,
            Intent("SMS_SENT"),
            0
        )
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, data, sendPI, null)
    }
}