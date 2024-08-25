package com.mm.contact.data.repository

import com.mm.contact.data.local.MMDatabase
import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.data.toModel
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsRepositoryImp(
    private val contactDb: MMDatabase,
) : ContactsRepository {
    override fun getAllContact(): Flow<List<Contact>> {
        return contactDb.getContactDao().getContacts().map { it.map { c -> c.toModel() } }
    }


    override suspend fun getAllContact(
        page: Int,
        pageSize: Int,
    ): Flow<List<Contact>> = contactDb.getContactDao()
        .getContacts()
        .map { it.map { c -> c.toModel() } }

    override suspend fun insertContact(contact: ContactEntity) {
        return contactDb.getContactDao().insertContact(contact)
    }

}