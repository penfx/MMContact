package com.mm.contact.data.repository

import android.util.Log
import com.mm.contact.data.local.MMDatabase
import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.data.toModel
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import com.mm.contact.util.Constant.LIMIT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsRepositoryImp(
    private val contactDb: MMDatabase,
) : ContactsRepository {
    override fun getAllContacts(): Flow<List<Contact>> {
        return contactDb.getContactDao().getContacts().map { it.map { c -> c.toModel() } }
    }
    override suspend fun getContacts(
        page: Int,
    ): Flow<List<Contact>> {
        return contactDb.getContactDao()
            .getContacts(offset = page * LIMIT)
            .map { it.map { c -> c.toModel() } }
    }

    override suspend fun insertContact(contact: ContactEntity) {
        return contactDb.getContactDao().insertContact(contact)
    }

}