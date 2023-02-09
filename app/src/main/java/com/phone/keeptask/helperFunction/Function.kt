package com.phone.keeptask.helperFunction

import android.content.Context
import android.widget.Toast

object Functions {

    fun toast(context : Context, message :String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}