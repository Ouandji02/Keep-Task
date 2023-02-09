package com.phone.keeptask.data.contacts

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.annotation.WorkerThread
import com.phone.keeptask.domain.model.Contact

class ContentResolverHelper(private val context: Context) {
    private fun getContentResolver(): ContentResolver {
        return context.contentResolver
    }

    private var mCursor: Cursor? = null
    private val mProjetction = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
        ContactsContract.Contacts._ID
    )
    private val sorted = "${ContactsContract.Contacts.DISPLAY_NAME} ASC"

    @WorkerThread
    fun getAllContact() : List<Contact> = getDataContact()

    private fun getDataContact(): MutableList<Contact> {
        val contactList = mutableListOf<Contact>()
        mCursor = getContentResolver().query(
            ContactsContract.Contacts.CONTENT_URI,
            mProjetction,
            null,
            null,
            sorted
        )
        mCursor?.use {
            val displayName = it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
            val hasPhone = it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val id = it.getColumnIndexOrThrow(ContactsContract.Contacts._ID)

            it.apply {
                if (count == 0) println("IL Y A PAS DE CONTACT")
                else {
                    while (it.moveToNext()) {
                        val name = getString(displayName)
                        val hasTel = getInt(hasPhone)
                        val idTel = getString(id)
                        if (hasTel > 0) {
                            val pCursor = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
                                arrayOf(idTel),
                                null
                            )
                            pCursor?.apply {
                                if (count == 0) println("IL N'A PAS DE NUMBER")
                                else {
                                    while (moveToNext()) {
                                        val phone =
                                            getString(this.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                        contactList += Contact(
                                            name = name,
                                            tel = phone,
                                            uri = ContentUris.withAppendedId(
                                                ContactsContract.Contacts.CONTENT_URI,
                                                idTel.toLong()
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return contactList
    }
}