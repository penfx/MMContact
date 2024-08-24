package com.mm.contact.data.provider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.mm.contact.data.entities.ContactEntity
import com.mm.contact.data.entities.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactProvider @Inject constructor(@ApplicationContext private val context: Context) {
    private val resolver = context.contentResolver

    fun getContacts(page: Int, pageSize: Int ,searchQuery: String? = null): Result<List<ContactEntity>> {

        return try {
            val contacts = getContactsFromProvider(
                page = page,
                pageSize = pageSize,
                searchQuery = searchQuery
            )
            Result.Success(contacts)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    private fun getContactsFromProvider(
        page: Int,
        pageSize: Int,
        searchQuery: String? = null
    ): List<ContactEntity> {
        val resolver = context.contentResolver

        // Build the URI with the starting position, limit, and search query
        val builder = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, "raw_contacts")
            .buildUpon()
        builder
            .appendQueryParameter("limit", pageSize.toString())
            .appendQueryParameter("offset", (page * pageSize).toString())
        if (searchQuery != null) {
            builder.appendQueryParameter(
                "query",
                searchQuery // Add the search query to the URI
            )
        }
        val uri = builder.build()

        val cursor = resolver.query(uri, null, null, null, null)
        var contacts = mutableListOf<ContactEntity>()
        cursor?.use {
                contacts = extractContactFromCursor(it).toMutableList()
        }

        return contacts
    }


    private fun extractContactFromCursor(cursor: Cursor): List<ContactEntity> {
        val contacts = mutableListOf<ContactEntity>()
        // Use a ContentResolver to query the Contacts Provider
        cursor.use {
            while (it.moveToNext()) {
                contacts.add(
                    ContactEntity(
                        name = getName(cursor) ?: "Unknown",
                        phoneNumber = getPhoneNumber(cursor),
                        email = getEmail(cursor)
                    )
                ) // Add to the list
            }
        }
        cursor.close()
        return contacts;
    }

    private fun getName(cursor: Cursor): String? {
        return cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
    }

    private fun getPhoneNumber(cursor: Cursor): String? {
        val hasPhone = cursor.getInt(
            cursor.getColumnIndexOrThrow(
                ContactsContract.Contacts.HAS_PHONE_NUMBER
            )
        ) == 1
        if (hasPhone) {

            return cursor.getString(
                cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )
        }
        return ""
    }

    private fun getEmail(cursor: Cursor): String {
        var emailAddress = "";
        val hasEmail =
            cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)) == 1
        if (hasEmail) {
            cursor.use {
                emailAddress = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                )
            }
        }
        return emailAddress;
    }
}