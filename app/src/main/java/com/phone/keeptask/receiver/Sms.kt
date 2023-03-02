package com.phone.keeptask.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import com.phone.keeptask.helperFunction.Functions

class SmS(private val context: Context) {
    fun sendSmS(phoneNumber: String, data: String) {
        val sendPI = PendingIntent.getBroadcast(
            context,
            0,
            Intent("SMS_SENT"),
            0
        )
        val piDelivered = PendingIntent.getBroadcast(context, 0, Intent("SMS_DELIVERED"), 0)
        try {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, data, null, null)
            Functions.toast(context, "Message envoye a $phoneNumber")
        } catch (e: Exception) {
            Functions.toast(context, e.message.toString())
        }

    }

/*
    private fun sendSms(phonenumber: String, message: String, isBinary: Boolean) {
        val manager = SmsManager.getDefault()
        val piSend = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), 0)
        val piDelivered = PendingIntent.getBroadcast(context, 0, Intent("SMS_DELIVERED"), 0)
        if (isBinary) {
            val data = ByteArray(message.length)
            var index = 0
            while (index < message.length && index < 50) {
                data[index] = message[index].code.toByte()
                ++index
            }
            manager.sendDataMessage(phonenumber, null, SMS_PORT as Short, data, piSend, piDelivered)
        } else {
            val length = message.length
            if (length > 50) {
                val messagelist = manager.divideMessage(message)
                manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null)
            } else {
                manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered)
            }
        }
    }
*/
}