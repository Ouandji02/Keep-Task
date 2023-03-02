package com.phone.keeptask.helperFunction

import android.content.Context
import android.widget.Toast
import com.phone.keeptask.domain.model.Contact

object Functions {

    fun toast(context : Context, message :String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun extratNumber(listContact: List<Contact>, name: String): String {
        return listContact.filter { contact -> name.contains(contact.name) }[0].tel
    }

}